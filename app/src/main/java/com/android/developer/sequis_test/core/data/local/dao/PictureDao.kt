package com.android.developer.sequis_test.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
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
    @Query("SELECT * FROM PictureEntity")
    fun getPictures(): PagingSource<Int, PictureEntity>


    /**
     * Get picture from DataBase by id
     *
     * @return Flow of [PictureEntity]
     */
    @Query("SELECT * FROM PictureEntity WHERE id = :id")
    fun getPicture(id: String): Flow<PictureEntity>

    /**
     * Save pictures but when pictures already saved replace with the new one
     *
     * @param pictures list of [PictureEntity]
     * @see OnConflictStrategy.REPLACE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putPictures(pictures: List<PictureEntity>)

    /**
     * Delete selected list picture
     *
     * @param pictures list of [PictureEntity]
     * @see Delete
     */
    @Delete
    fun deletePictures(pictures: List<PictureEntity>)
}