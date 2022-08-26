package com.android.developer.sequis_test.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity represents remote key for located picture
 */
@Entity
data class PictureKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?,
)
