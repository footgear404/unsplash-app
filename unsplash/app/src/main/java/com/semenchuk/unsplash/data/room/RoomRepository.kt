package com.semenchuk.unsplash.data.room

import androidx.paging.PagingSource
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

class RoomRepository(
    private val unsplashDatabaseDao: UnsplashDatabaseDao
) {
    fun getPagingSource(): PagingSource<Int, SavedPhotoEntity> {
        return unsplashDatabaseDao.getPhotosPagingSource()
    }

    suspend fun save(saved_photos: List<SavedPhotoEntity>) {
        unsplashDatabaseDao.save(saved_photos)
    }

    suspend fun refresh(saved_photos: List<SavedPhotoEntity>) {
        unsplashDatabaseDao.refresh(saved_photos)
    }

}