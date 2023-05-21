package com.semenchuk.unsplash.data.room

import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

class RoomRepository(
    private val unsplashDatabaseDao: UnsplashDatabaseDao
) {
    suspend fun save(saved_photos: List<SavedPhotoEntity>) {
        unsplashDatabaseDao.save(saved_photos)
    }

    suspend fun refresh(saved_photos: List<SavedPhotoEntity>) {
        unsplashDatabaseDao.refresh(saved_photos)
    }

}