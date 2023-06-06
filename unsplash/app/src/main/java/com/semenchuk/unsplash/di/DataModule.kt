package com.semenchuk.unsplash.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.room.RoomRepository
import com.semenchuk.unsplash.data.room.UnsplashDatabase
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

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
    fun provideSharedPrefs(application: Application): SharedPreferences {
        return application.applicationContext
            .getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
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

//    @OptIn(ExperimentalPagingApi::class)
//    @Provides
//    fun provideSavedPhotosRemoteMediator(
//        unsplashDatabaseDao: UnsplashDatabaseDao,
//        retrofitService: RetrofitService
//    ): SavedPhotosRemoteMediator {
//        return SavedPhotosRemoteMediator(unsplashDatabaseDao, retrofitService)
//    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun provideUnsplashRepository(
        unsplashDatabaseDao: UnsplashDatabaseDao,
        retrofitService: RetrofitService
    ): UnsplashRepository {
        return UnsplashRepository(unsplashDatabaseDao, retrofitService)
    }
}