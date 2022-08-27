package com.android.developer.sequis_test.detail.data.repo

import com.android.developer.sequis_test.core.data.local.dao.CommentDao
import com.android.developer.sequis_test.core.data.local.dao.PictureDao
import com.android.developer.sequis_test.core.data.local.entities.backToEntity
import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.domain.relations.PictureAndComments
import com.android.developer.sequis_test.detail.domain.repo.DetailRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class DetailRepoImpl @Inject constructor(
    private val pictureDao: PictureDao,
    private val commentDao: CommentDao,
) : DetailRepo {

    override fun getPictureAndComments(id: String): Flow<PictureAndComments> =
        pictureDao.getPictureAndComments(id).map { data -> data.toModel() }

    override suspend fun putComment(comment: Comment): Long =
        commentDao.putComment(comment.backToEntity())

    override suspend fun delComment(comment: Comment) =
        commentDao.delComment(comment.backToEntity())
}