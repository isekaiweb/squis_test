package com.android.developer.sequis_test.core.di

import android.app.Application
import androidx.room.Room
import com.android.developer.sequis_test.core.data.local.PicturesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    /**
     * Create provider method binding for [PicturesDb]
     *
     * @see Provides
     */

    @Provides
    @Singleton
    fun provideDataBase(app: Application): PicturesDb =
        Room.databaseBuilder(app, PicturesDb::class.java, "pictures_db")
            .fallbackToDestructiveMigration()
            .build()


    /**
     * Create provider method binding for [PicturesDb.pictureDao]
     *
     * @see Provides
     */
    @Provides
    @Singleton
    fun providePictureDao(db: PicturesDb) = db.pictureDao()


    /**
     * Create provider method binding for [PicturesDb.pictureKeyDao]
     *
     * @see Provides
     */
    @Provides
    @Singleton
    fun providePictureKeyDao(db: PicturesDb) = db.pictureKeyDao()

}