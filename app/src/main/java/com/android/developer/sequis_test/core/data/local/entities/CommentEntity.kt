package com.android.developer.sequis_test.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.developer.sequis_test.core.domain.model.Comment

@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = false)
    val createdAt: Long = System.currentTimeMillis(),
    val pictureId: String,
    val content: String,
) {
    fun toModel() = Comment(
        pictureId,
        content,
        createdAt
    )
}