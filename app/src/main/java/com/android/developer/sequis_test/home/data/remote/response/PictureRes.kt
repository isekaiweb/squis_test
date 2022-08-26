package com.android.developer.sequis_test.home.data.remote.response

import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.google.gson.annotations.SerializedName

data class PictureRes(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("download_url") val url: String,
) {
    fun toEntity() = PictureEntity(id, author, url)
}
