package com.android.developer.sequis_test.core.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.android.developer.sequis_test.core.data.local.entities.CommentEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.android.developer.sequis_test.core.domain.relations.PictureAndComments

data class PictureEntityAndCommentsEntity(
    @Embedded val picture: PictureEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pictureId"
    )
    val comments: List<CommentEntity>
) {
    fun toModel() = PictureAndComments(picture.toModel(), comments.map { it.toModel() })
}
