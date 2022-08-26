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
    val comments: List<Comment> = emptyList()
) {
    data class Comment(
        @PrimaryKey(autoGenerate = true)
        val id: String,
        val author: String,
        val content: String,
        val createdAt: Long = System.currentTimeMillis()
    )

    private fun commentsToModel(): List<Picture.Comment> {
        return comments.map { comment ->
            Picture.Comment(comment.author, comment.content, comment.createdAt)
        }
    }

    fun toModel(): Picture {
        return Picture(id, author, url, commentsToModel())
    }
}