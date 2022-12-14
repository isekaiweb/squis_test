package com.android.developer.sequis_test.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.developer.sequis_test.core.data.local.dao.CommentDao
import com.android.developer.sequis_test.core.data.local.dao.PictureDao
import com.android.developer.sequis_test.core.data.local.dao.PicturesKeyDao
import com.android.developer.sequis_test.core.data.local.entities.CommentEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureKeyEntity


/**
 * Story App database storing all information ini this module like: data pictures, comment and key of picture
 *
 *@see Database
 */
@Database(
    entities = [
        PictureEntity::class,
        PictureKeyEntity::class,
        CommentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PicturesDb : RoomDatabase() {

    /**
     * Get picture data access object
     *
     * @return [PictureDao]
     */
    abstract fun pictureDao(): PictureDao

    /**
     * Get picture key data access object
     *
     * @return [PicturesKeyDao]
     */
    abstract fun pictureKeyDao(): PicturesKeyDao

    /**
     * Get comment data access object
     *
     * @return [CommentDao]
     */
    abstract fun commentDao(): CommentDao


}