package com.semenchuk.unsplash.data

import android.util.Log
import androidx.paging.*
import com.semenchuk.unsplash.AUTH_STATUS
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.like.models.LikeResponse
import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
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
        return retrofitService.getPhotoById.send("Bearer ${sp.getString(AUTH_STATUS, null)}", id)
    }

    suspend fun addLike(id:String, likedByUser: Boolean): Response<LikeResponse> {
        val db = unsplashDatabaseDao.updateLike(id, likedByUser = !likedByUser)
        Log.d("DB", "addLike to db: ${!likedByUser} status-code($db)")
        return retrofitService.likePhoto.send("Bearer ${sp.getString(AUTH_STATUS, null)}", id)
    }

    suspend fun removeLike(id:String, likedByUser: Boolean): Response<LikeResponse> {
        val db = unsplashDatabaseDao.updateLike(id, likedByUser = !likedByUser)
        Log.d("DB", "removeLike from db: ${!likedByUser} status-code($db)")
        return retrofitService.unLikePhoto.send("Bearer ${sp.getString(AUTH_STATUS, null)}", id)
    }
}