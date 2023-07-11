package com.semenchuk.unsplash.data

import android.util.Log
import androidx.paging.*
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.appAuth.AuthRepository
import com.semenchuk.unsplash.data.appAuth.AuthRepository.Companion.ACCESS_TOKEN
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.like.models.LikeResponse
import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.data.room.photos.SavedPhotosRemoteMediator
import com.semenchuk.unsplash.data.room.profile.SavedProfile
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class UnsplashRepository(
    private val unsplashDatabaseDao: UnsplashDatabaseDao,
    private val retrofitService: RetrofitService
) {

    private val sp = App.appComponent.sharedPrefs()

    fun pagerForFeed(query: String): Pager<Int, SavedPhotoEntity> {
        val savedPhotosRemoteMediator = SavedPhotosRemoteMediator(unsplashDatabaseDao, retrofitService, query)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = savedPhotosRemoteMediator,
            initialKey = null,
            pagingSourceFactory = {
                savedPhotosRemoteMediator.unsplashDatabaseDao.getPhotosPagingSource()
            }
        )
    }

    suspend fun getPhotoById(id: String): Response<DetailedPhoto> {
        return retrofitService.getPhotoById.send("Bearer ${sp.getString(ACCESS_TOKEN, null)}", id)
    }

    suspend fun addLike(id:String, likedByUser: Boolean): Response<LikeResponse> {
        val db = unsplashDatabaseDao.updateLike(id, likedByUser = !likedByUser)
        Log.d("DB", "addLike to db: ${!likedByUser} status-code($db)")
        return retrofitService.likePhoto.send("Bearer ${sp.getString(ACCESS_TOKEN, null)}", id)
    }

    suspend fun removeLike(id:String, likedByUser: Boolean): Response<LikeResponse> {
        val db = unsplashDatabaseDao.updateLike(id, likedByUser = !likedByUser)
        Log.d("DB", "removeLike from db: ${!likedByUser} status-code($db)")
        return retrofitService.unLikePhoto.send("Bearer ${sp.getString(ACCESS_TOKEN, null)}", id)
    }

    suspend fun getUserProfile(): Response<ProfileDto> {
        return retrofitService.userProfile.send("Bearer ${sp.getString(ACCESS_TOKEN, null)}")
    }

    suspend fun saveUserProfile(profile: SavedProfile) {
        unsplashDatabaseDao.saveProfile(profile)
    }

    suspend fun loadUserProfile(): SavedProfile {
        return unsplashDatabaseDao.selectUserProfile()
    }

    suspend fun logout(): Boolean {
        val result = unsplashDatabaseDao.logout()

        val editor = sp.edit()
        editor.putString(ACCESS_TOKEN, null)
        editor.putString(AuthRepository.TOKEN_TYPE, null)
        editor.putString(AuthRepository.TOKEN_SCOPE, null)
        editor.apply()

        Log.d("LOGOUT", "DB was cleared: $result")
        Log.d("LOGOUT", "SP status: ${sp.all}")
        return result
    }
}