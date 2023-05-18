package com.semenchuk.unsplash.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.semenchuk.unsplash.data.SavedPhotosRemoteMediator
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import com.semenchuk.unsplash.data.room.RoomRepository
import com.semenchuk.unsplash.data.room.UnsplashDatabase
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    fun providePhotosPagingSource(retrofitService: RetrofitService): PhotosPagingSource {
        return PhotosPagingSource(retrofitService)
    }


    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitService {
        return RetrofitService()
    }

    @Provides
    @Singleton
    fun provideUnsplashDatabase(application: Application): UnsplashDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            UnsplashDatabase::class.java,
            name = "app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUnsplashDatabaseDao(db: UnsplashDatabase): UnsplashDatabaseDao {
        return db.appDatabaseDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(database: UnsplashDatabase): RoomRepository {
        return RoomRepository(database.appDatabaseDao())
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun provideSavedPhotosRemoteMediator(
        unsplashDatabaseDao: UnsplashDatabaseDao,
        retrofitService: RetrofitService
    ): SavedPhotosRemoteMediator {
        return SavedPhotosRemoteMediator(unsplashDatabaseDao, retrofitService)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun provideUnsplashRepository(
        savedPhotosRemoteMediator: SavedPhotosRemoteMediator
    ): UnsplashRepository {
        return UnsplashRepository(savedPhotosRemoteMediator)
    }
}