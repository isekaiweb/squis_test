package com.android.developer.sequis_test.home.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.android.developer.sequis_test.core.data.local.dao.PictureDao
import com.android.developer.sequis_test.core.data.local.dao.PicturesKeyDao
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.home.data.paging.PictureRemoteMediator
import com.android.developer.sequis_test.home.data.remote.HomeApi
import com.android.developer.sequis_test.home.domain.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepoImpl @Inject constructor(
    private val api: HomeApi,
    private val pictureDao: PictureDao,
    private val picturesKeyDao: PicturesKeyDao
) : HomeRepo {

    override fun getPictures(lastSizeLoaded: Int): Flow<PagingData<Picture>> {
        val pagingSourceFactory = { pictureDao.getPictures() }

        return Pager(
            config = PagingConfig(
                pageSize = lastSizeLoaded,
                enablePlaceholders = true
            ),
            remoteMediator = PictureRemoteMediator({ page ->
                runBlocking { api.getPictures(page) }
            }, pictureDao, picturesKeyDao),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map { data -> data.toModel() } }
    }

}