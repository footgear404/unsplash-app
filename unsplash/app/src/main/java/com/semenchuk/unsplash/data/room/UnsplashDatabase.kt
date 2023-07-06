package com.semenchuk.unsplash.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.data.room.profile.SavedProfile

@Database(entities = [SavedPhotoEntity::class, SavedProfile::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun appDatabaseDao(): UnsplashDatabaseDao
}