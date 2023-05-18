package com.semenchuk.unsplash.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

@Database(entities = [SavedPhotoEntity::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun appDatabaseDao(): UnsplashDatabaseDao
}