package com.android.developer.sequis_test.core.data.local.dao

import androidx.room.*
import com.android.developer.sequis_test.core.data.local.entities.PictureKeyEntity

@Dao
interface PicturesKeyDao {
    /**
     * Get pictures key where id same with @param id
     *
     * @param id from entity picture key
     * @return entity picture key
     * @see PictureKeyEntity
     */
    @Query("SELECT * FROM PictureKeyEntity WHERE id = :id")
    suspend fun getKey(id: String): PictureKeyEntity?

    /**
     * Save pictures key but if already saved then replace with the new one
     *
     * @param keys list of entity picture key
     * @see OnConflictStrategy.REPLACE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putKeys(keys: List<PictureKeyEntity>)
}