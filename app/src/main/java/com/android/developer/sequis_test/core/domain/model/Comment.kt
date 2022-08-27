package com.android.developer.sequis_test.core.domain.model

data class Comment(
    val currentTimeAt: Long,
    val pictureId: String,
    val author: String,
    val content: String,
    val createdAt: String = "",
)