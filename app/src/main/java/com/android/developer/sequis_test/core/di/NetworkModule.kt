package com.android.developer.sequis_test.core.di

import com.android.developer.sequis_test.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECTION_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 60L
private const val BASE_URL = "https://picsum.photos/v2/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Create a provider method binding for [HttpLoggingInterceptor]
     *
     * @return Instance of http interceptor.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    /**
     * Create a provider method binding for [OkHttpClient].
     *
     * @return Instance of OkHttpClient.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor).build()


    /**
     * Create a provider method binding for [Retrofit].
     *
     * @return Instance of retrofit.
     * @see Provides
     **/

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}