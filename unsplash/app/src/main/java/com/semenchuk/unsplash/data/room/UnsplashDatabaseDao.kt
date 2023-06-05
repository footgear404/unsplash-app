package com.semenchuk.unsplash.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

@Dao
interface UnsplashDatabaseDao {

    @Query("SELECT * FROM saved_photo")
    fun getPhotosPagingSource(): PagingSource<Int, SavedPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(saved_photos: List<SavedPhotoEntity>)

    @Query("DELETE FROM saved_photo")
    suspend fun clear()

    @Transaction
    suspend fun refresh(saved_photos: List<SavedPhotoEntity>) {
        clear()
        save(saved_photos)
    }
}