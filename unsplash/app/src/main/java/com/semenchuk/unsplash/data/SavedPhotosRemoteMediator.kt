package com.semenchuk.unsplash.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.semenchuk.unsplash.AUTH_STATUS
import com.semenchuk.unsplash.App
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.domain.utils.Mappers

@ExperimentalPagingApi
class SavedPhotosRemoteMediator(
    val unsplashDatabaseDao: UnsplashDatabaseDao,
    private val retrofitService: RetrofitService
) : RemoteMediator<Int, SavedPhotoEntity>() {

    private val sp = App.appComponent.sharedPrefs()

    private var pageIndex = FIRST_PAGE

    var savedPhotos: List<SavedPhotoEntity>? = emptyList()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SavedPhotoEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize

        Log.d("TAG", "getPageIndex: $pageIndex")
        Log.d("TAG", "limit: $limit")

        return try {
            savedPhotos = fetchPhotos(pageIndex)

            unsplashDatabaseDao.save(savedPhotos!!)

            Log.d("TAG", "size: ${savedPhotos!!.size}")
            MediatorResult.Success(endOfPaginationReached = savedPhotos!!.size < limit)
        } catch (e: Exception) {
            Log.d("TAG", "load: $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchPhotos(
        page: Int
    ): List<SavedPhotoEntity>? {
        val response = retrofitService.getPhotos.send(
            authHeader = "Bearer ${sp.getString(AUTH_STATUS, null)}",
            page = page,
            per_page = PAGE_SIZE
        )
        return response.body()?.let { Mappers.toSavedPhotoEntity(it) }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        Log.d("TAG", "loadType: $loadType")
        pageIndex = when (loadType) {
            LoadType.REFRESH -> FIRST_PAGE
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    suspend fun refresh() {
        pageIndex = FIRST_PAGE
        unsplashDatabaseDao.clear()
    }

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
    }

}