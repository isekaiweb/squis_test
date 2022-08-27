package com.android.developer.sequis_test.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.developer.sequis_test.core.domain.model.Picture

@Entity
data class PictureEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val author: String,
    val url: String,
) {
    fun toModel() = Picture(id, author, url)
}