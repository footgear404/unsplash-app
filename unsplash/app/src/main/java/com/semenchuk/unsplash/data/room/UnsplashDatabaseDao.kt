package com.semenchuk.unsplash.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

@Dao
interface UnsplashDatabaseDao {

    @Query("SELECT * FROM saved_photo")
    fun getPhotosPagingSource(): PagingSource<Int, SavedPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(saved_photos: List<SavedPhotoEntity>)

    @Query("DELETE FROM saved_photo")
    suspend fun clear()

    @Query("UPDATE saved_photo SET likedByUser = :likedByUser WHERE id = :id")
    suspend fun updateLike(id: String, likedByUser: Boolean): Int

    @Transaction
    suspend fun refresh(saved_photos: List<SavedPhotoEntity>) {
        clear()
        save(saved_photos)
    }
}