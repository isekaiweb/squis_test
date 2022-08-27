package com.android.developer.sequis_test.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.developer.sequis_test.core.data.local.entities.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun putComment(comment: CommentEntity): Long

    @Delete
    suspend fun delComment(comment: CommentEntity)
}