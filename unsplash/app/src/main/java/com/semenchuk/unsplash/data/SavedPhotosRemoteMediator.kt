package com.semenchuk.unsplash.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.domain.utils.Mappers

@ExperimentalPagingApi
class SavedPhotosRemoteMediator(
    val unsplashDatabaseDao: UnsplashDatabaseDao,
    private val retrofitService: RetrofitService
) : RemoteMediator<Int, SavedPhotoEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SavedPhotoEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize

        return try {
            val savedPhotos = fetchPhotos(pageIndex)

            if (loadType == LoadType.REFRESH) {
                unsplashDatabaseDao.refresh(savedPhotos!!)
            } else {
                unsplashDatabaseDao.save(savedPhotos!!)
            }
            MediatorResult.Success(endOfPaginationReached = savedPhotos.size < limit)
        } catch (e: Exception) {
            Log.d("TAG", "load: $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchPhotos(
        page: Int
    ): List<SavedPhotoEntity>? {
        val response = retrofitService.getPhotos.send(
            authHeader = "Bearer yLA2KWq1NDPo0pgq3pD6aEkMBsD5zfn-gsMXmJSVoGc",
            page = page
        )

        return response.body()?.let { Mappers.toSavedPhotoEntity(it) }

    }

    private fun getPageIndex(loadType: LoadType): Int? {
        Log.d("TAG", "getPageIndex: $pageIndex")
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

}