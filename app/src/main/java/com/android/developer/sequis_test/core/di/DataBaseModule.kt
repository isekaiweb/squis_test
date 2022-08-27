package com.android.developer.sequis_test.core.di

import android.app.Application
import androidx.room.Room
import com.android.developer.sequis_test.BuildConfig
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
        Room.databaseBuilder(
            app,
            PicturesDb::class.java,
            "${BuildConfig.APPLICATION_ID}_pictures_db"
        ).fallbackToDestructiveMigration()
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
     * Create provider method binding for [PicturesDb.commentDao]
     *
     * @see Provides
     */
    @Provides
    @Singleton
    fun provideCommentDao(db: PicturesDb) = db.commentDao()

}