package com.android.developer.sequis_test.detail.domain.usecase

import com.android.developer.sequis_test.core.domain.relations.PictureAndComments
import com.android.developer.sequis_test.detail.domain.repo.DetailRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ViewModelScoped
class GetPictureAndComments @Inject constructor(
    private val detailRepo: DetailRepo,
) {
    operator fun invoke(id: String): Flow<PictureAndComments> =
        detailRepo.getPictureAndComments(id).map { dataFlow ->
            val comments = dataFlow.comments.sortedByDescending { it.currentTimeAt }.map { comment ->
                comment.copy(createdAt = comment.currentTimeAt.generateCreatedAt())
            }
            PictureAndComments(dataFlow.picture, comments)
        }.distinctUntilChanged()

    private fun Long.generateCreatedAt(): String {
        val currentTime = System.currentTimeMillis()
        val minutesPassed = TimeUnit.MILLISECONDS.toMinutes(currentTime - this).toInt()

        return when{
            minutesPassed < 1 -> "just now"

            minutesPassed < 60 -> {
                "$minutesPassed minutes ago"
            }
            minutesPassed < 1440 -> {
                "${minutesPassed / 60} hours ago"
            }
            else -> {
                "${minutesPassed / 1440} days ago"
            }
        }

    }
}