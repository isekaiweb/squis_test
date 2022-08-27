package com.android.developer.sequis_test.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.presentation.base.BaseViewModel
import com.android.developer.sequis_test.detail.domain.usecase.DelComment
import com.android.developer.sequis_test.detail.domain.usecase.GetPictureAndComments
import com.android.developer.sequis_test.detail.domain.usecase.PutComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPictureAndComments: GetPictureAndComments,
    private val putComment: PutComment,
    private val delComment: DelComment,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<DetailState, DetailEvent>(DetailState()) {

    init {
        setData()
    }

    override fun DetailEvent.onEvent() {
        when (this) {
            is DetailEvent.PerformAddComment -> addComment(comment, callBackSuccessAdd)
            is DetailEvent.PerformDeleteComment -> deleteComment(comment)
        }

    }

    private fun deleteComment(comment: Comment) {
        viewModelScope.launch { delComment(comment) }
    }

    private fun addComment(comment: Comment, callBackSuccessAdd: () -> Unit) {
        viewModelScope.launch {
            if (putComment(comment) != -1L) {
                delay(0.5.seconds)
                callBackSuccessAdd()
            }
        }
    }

    private fun setData() {
        val pictureId = savedStateHandle.get<String>(KEY)!!

        viewModelScope.launch {
            getPictureAndComments(pictureId).collectLatest { pictureAndComments ->
                setState { state -> state.copy(pictureAndComments = pictureAndComments) }
            }
        }
    }


    companion object {
        private const val KEY = "pictureId"
    }
}