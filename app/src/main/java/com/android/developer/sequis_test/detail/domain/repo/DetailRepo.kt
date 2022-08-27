package com.android.developer.sequis_test.detail.domain.repo

import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.domain.relations.PictureAndComments
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
interface DetailRepo {
    fun getPictureAndComments(id: String): Flow<PictureAndComments>

    suspend fun putComment(comment: Comment): Long

    suspend fun delComment(comment: Comment)
}