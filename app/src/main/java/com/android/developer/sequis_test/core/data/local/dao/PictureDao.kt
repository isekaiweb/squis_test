package com.android.developer.sequis_test.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.android.developer.sequis_test.core.data.local.relations.PictureEntityAndCommentsEntity
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PictureDao {

    /**
     * Get pictures from DataBase
     *
     * @return paging source
     * @see PagingSource
     */
    @Query("SELECT * FROM PictureEntity ORDER BY id ASC")
    fun getPictures(): PagingSource<Int, PictureEntity>


    /**
     * Get picture from DataBase by id
     *
     * @return Flow of [PictureEntity]
     */
    @Transaction
    @Query("SELECT * FROM PictureEntity WHERE id = :id")
    fun getPictureAndComments(id: String): Flow<PictureEntityAndCommentsEntity>

    /**
     * Save pictures but when pictures already saved replace with the new one
     *
     * @param pictures list of [PictureEntity]
     * @see OnConflictStrategy.REPLACE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putPictures(pictures: List<PictureEntity>)
}