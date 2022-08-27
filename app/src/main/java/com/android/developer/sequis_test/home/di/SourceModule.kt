package com.android.developer.sequis_test.home.di

import com.android.developer.sequis_test.home.data.repo.HomeRepoImpl
import com.android.developer.sequis_test.home.domain.repo.HomeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {
    @Binds
    fun bindRepo(impl: HomeRepoImpl): HomeRepo
}