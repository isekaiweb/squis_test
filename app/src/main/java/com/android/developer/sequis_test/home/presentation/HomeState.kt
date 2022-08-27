package com.android.developer.sequis_test.home.presentation

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.core.presentation.plugins.UIState

data class HomeState(
    val pictures: PagingData<Picture> = PagingData.empty(),
    val homeLoad: CombinedLoadStates? = null,
) : UIState
