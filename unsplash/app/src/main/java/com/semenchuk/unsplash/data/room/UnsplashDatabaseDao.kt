package com.semenchuk.unsplash.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.data.room.profile.SavedProfile

@Dao
interface UnsplashDatabaseDao {

    @Query("SELECT * FROM saved_photo")
    fun getPhotosPagingSource(): PagingSource<Int, SavedPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(saved_photos: List<SavedPhotoEntity>)

    @Query("DELETE FROM saved_photo")
    suspend fun clearPhotos(): Int

    @Query("DELETE FROM saved_profile")
    suspend fun clearProfile(): Int

    @Query("UPDATE saved_photo SET likedByUser = :likedByUser WHERE id = :id")
    suspend fun updateLike(id: String, likedByUser: Boolean): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: SavedProfile)

    @Query("SELECT * FROM saved_profile")
    suspend fun selectUserProfile(): SavedProfile

    @Transaction
    suspend fun refresh(saved_photos: List<SavedPhotoEntity>) {
        clearPhotos()
        save(saved_photos)
    }
    @Transaction
    suspend fun logout(): Boolean {
        return clearPhotos() + clearProfile() > 0
    }
}