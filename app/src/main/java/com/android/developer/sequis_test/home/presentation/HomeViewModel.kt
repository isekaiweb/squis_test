package com.android.developer.sequis_test.home.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.developer.sequis_test.core.presentation.base.BaseViewModel
import com.android.developer.sequis_test.home.domain.usecase.GetPictures
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPictures: GetPictures,
) : BaseViewModel<HomeState, HomeEvent>(HomeState()) {


    init {
        setData()
    }

    override fun HomeEvent.onEvent() {
        when (this) {
            is HomeEvent.PerformGetLoadState -> {
                setState { state -> state.copy(homeLoad = loadState) }
            }
        }
    }


    private fun setData(

    ) {
        viewModelScope.launch {
            getPictures()
                .cachedIn(viewModelScope)
                .collectLatest { pictures ->
                    Timber.e("RUN COLLECT")
                    setState { state -> state.copy(pictures = pictures) }
                }
        }

    }
}