package com.android.developer.sequis_test.detail.domain.usecase

import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.detail.domain.repo.DetailRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DelComment @Inject constructor(
    private val repo: DetailRepo
) {
    suspend operator fun invoke(comment: Comment) = repo.delComment(comment)
}