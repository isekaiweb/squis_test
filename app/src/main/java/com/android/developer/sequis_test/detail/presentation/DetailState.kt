package com.android.developer.sequis_test.detail.presentation

import com.android.developer.sequis_test.core.domain.relations.PictureAndComments
import com.android.developer.sequis_test.core.presentation.plugins.UIState

data class DetailState(
    val pictureAndComments: PictureAndComments? = null,
) : UIState
