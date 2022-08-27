package com.android.developer.sequis_test.detail.presentation

import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.presentation.plugins.UIEvent

sealed class DetailEvent : UIEvent {
    data class PerformAddComment(val comment: Comment, val callBackSuccessAdd:  () -> Unit) :
        DetailEvent()

    data class PerformDeleteComment(val comment: Comment) : DetailEvent()
}
