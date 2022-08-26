package com.android.developer.sequis_test.home.domain.repo

import androidx.paging.PagingData
import com.android.developer.sequis_test.core.domain.model.Picture
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface HomeRepo {
    fun getPictures(lastSizeLoaded: Int): Flow<PagingData<Picture>>
}