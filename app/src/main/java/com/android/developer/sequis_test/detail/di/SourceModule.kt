package com.android.developer.sequis_test.detail.di

import com.android.developer.sequis_test.detail.data.repo.DetailRepoImpl
import com.android.developer.sequis_test.detail.domain.repo.DetailRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface SourceModule {

    @Binds
    fun bindDetailRepo(impl: DetailRepoImpl): DetailRepo
}