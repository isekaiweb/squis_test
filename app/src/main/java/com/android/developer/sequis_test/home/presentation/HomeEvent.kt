package com.android.developer.sequis_test.home.presentation

import androidx.paging.CombinedLoadStates
import com.android.developer.sequis_test.core.presentation.plugins.UIEvent

sealed class HomeEvent : UIEvent {
    data class PerformGetLoadState(val loadState: CombinedLoadStates) : HomeEvent()
}