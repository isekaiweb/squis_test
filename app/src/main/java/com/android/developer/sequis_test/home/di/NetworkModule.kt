package com.android.developer.sequis_test.home.di

import com.android.developer.sequis_test.home.data.remote.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provide instance of the [HomeApi] service
     **/
    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)
}