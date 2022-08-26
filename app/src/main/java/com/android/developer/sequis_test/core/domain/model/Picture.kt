package com.android.developer.sequis_test.core.domain.model

data class Picture(
    val id: String,
    val author: String,
    val url: String,
    val comments: List<Comment>,
) {
    data class Comment(
        val author: String,
        val content: String,
        val createdAt: Long,
    )
}
