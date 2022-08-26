package com.android.developer.sequis_test.home.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.android.developer.sequis_test.core.data.local.dao.PictureDao
import com.android.developer.sequis_test.core.data.local.dao.PicturesKeyDao
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureKeyEntity
import com.android.developer.sequis_test.home.data.remote.response.PictureRes

class PictureRemoteMediator(
    private val getPictures: suspend (Int) -> List<PictureRes>,
    private val pictureDao: PictureDao,
    private val pictureKeyDao: PicturesKeyDao
) : RemoteMediator<Int, PictureEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PictureEntity>
    ): MediatorResult {

        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val key = getKeyClosestToCurrentPosition(state)
                key?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val key = getKeyForFirstItem(state)
                val prevPage = key?.prevPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = key != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val key = getKeyForLastItem(state)
                val nextPage = key?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = key != null
                    )
                nextPage
            }
        }

        return try {
            val response = getPictures(currentPage)
            val pictureEntity = response.map { data -> data.toEntity() }
            val endOfPaginationReached = pictureEntity.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val key = pictureEntity.map { picture ->
                PictureKeyEntity(picture.id, prevPage, nextPage)
            }

            if (loadType == LoadType.REFRESH) {
                pictureDao.deletePictures(pictures = pictureEntity)
                pictureKeyDao.deleteKeys(keys = key)
            }

            pictureDao.putPictures(pictures = pictureEntity)
            pictureKeyDao.putKeys(keys = key)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(Throwable(e.message))
        }

    }


    private suspend fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pictureKeyDao.getKey(id = id)
            }
        }
    }

    private suspend fun getKeyForFirstItem(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { story ->
                pictureKeyDao.getKey(id = story.id)
            }
    }

    private suspend fun getKeyForLastItem(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { story ->
                pictureKeyDao.getKey(id = story.id)
            }
    }


}