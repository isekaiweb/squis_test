package com.android.developer.sequis_test.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.developer.sequis_test.core.domain.model.Comment

@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = false)
    val createdAt: Long,
    val pictureId: String,
    val author: String,
    val content: String,
) {
    fun toModel() = Comment(
        currentTimeAt = createdAt,
        pictureId = pictureId,
        author = author,
        content = content
    )
}

fun Comment.backToEntity() = CommentEntity(
    createdAt = currentTimeAt,
    pictureId = pictureId,
    author = author,
    content = content,
)