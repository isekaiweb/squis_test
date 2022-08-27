package com.android.developer.sequis_test.home.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.developer.sequis_test.core.data.local.PicturesDb
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureKeyEntity
import com.android.developer.sequis_test.core.data.util.Response
import com.android.developer.sequis_test.core.data.util.getResponse
import com.android.developer.sequis_test.home.data.remote.HomeApi
import timber.log.Timber

class PictureRemoteMediator(
    private val api: HomeApi,
    private val db: PicturesDb,
) : RemoteMediator<Int, PictureEntity>() {


    private val pictures = db.pictureDao()
    private val remoteKey = db.pictureKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PictureEntity>
    ): MediatorResult {


        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }

        Timber.e("PAGE: $currentPage")

        return when (val response =
            getResponse { api.getPictures(page = currentPage, limit = PAGE_SIZE) }) {
            is Response.Success -> {

                val pictures = response.data.map { data -> data.toEntity() }
                val endOfPaginationReached = pictures.isEmpty()
                val prevPage = if (currentPage == 1) null else currentPage - 1
                val nextPage = if (endOfPaginationReached) null else currentPage + 1

                val keys = pictures.map { picture ->
                    PictureKeyEntity(
                        id = picture.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                db.withTransaction {
                    this.pictures.putPictures(pictures = pictures)
                    remoteKey.putKeys(keys = keys)
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Response.Failed -> MediatorResult.Error(Throwable(response.message))
            is Response.ConnectionError -> MediatorResult.Error(Throwable(response.message))
            is Response.ServerError -> MediatorResult.Error(Throwable(response.message))
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKey.getKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { story ->
                remoteKey.getKey(id = story.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PictureEntity>,
    ): PictureKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { story ->
                remoteKey.getKey(id = story.id)
            }
    }

    companion object {
        const val PAGE_SIZE = 10
    }

}