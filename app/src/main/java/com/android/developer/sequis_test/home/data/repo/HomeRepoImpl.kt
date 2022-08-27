package com.android.developer.sequis_test.home.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.android.developer.sequis_test.core.data.local.PicturesDb
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.home.data.paging.PictureRemoteMediator
import com.android.developer.sequis_test.home.data.remote.HomeApi
import com.android.developer.sequis_test.home.domain.repo.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepoImpl @Inject constructor(
    private val api: HomeApi,
    private val db: PicturesDb,
) : HomeRepo {

    override fun getPictures(): Flow<PagingData<Picture>> {
        val pagingSourceFactory = { db.pictureDao().getPictures() }

        return Pager(
            config = PagingConfig(
                pageSize = PictureRemoteMediator.PAGE_SIZE,
            ),
            remoteMediator = PictureRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map { data -> data.toModel() } }
    }

}