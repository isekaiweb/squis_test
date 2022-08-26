package com.android.developer.sequis_test.home.data.remote

import com.android.developer.sequis_test.home.data.remote.response.PictureRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("list")
    suspend fun getPictures(
        @Query("page") page: Int,
    ): List<PictureRes>
}