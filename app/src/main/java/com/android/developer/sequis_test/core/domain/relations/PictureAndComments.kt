package com.android.developer.sequis_test.core.domain.relations

import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.domain.model.Picture

data class PictureAndComments(
    val picture: Picture,
    val comments: List<Comment>
)