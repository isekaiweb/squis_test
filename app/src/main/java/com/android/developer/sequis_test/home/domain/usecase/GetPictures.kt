package com.android.developer.sequis_test.home.domain.usecase

import androidx.paging.PagingData
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.home.domain.repo.HomeRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@ViewModelScoped
class GetPictures @Inject constructor(
    private val repo: HomeRepo
) {
    operator fun invoke(): Flow<PagingData<Picture>> = repo.getPictures()
        .distinctUntilChanged()
}