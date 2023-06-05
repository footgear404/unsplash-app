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

    private var pageIndex = 1

    var savedPhotos: List<SavedPhotoEntity>? = emptyList()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SavedPhotoEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize

        return try {
            savedPhotos = fetchPhotos(pageIndex)

            unsplashDatabaseDao.save(savedPhotos!!)

//            if (loadType == LoadType.REFRESH) {
//                unsplashDatabaseDao.refresh(savedPhotos!!)
//            } else {
//                unsplashDatabaseDao.save(savedPhotos!!)
//            }
            Log.d("TAG", "limit: ${savedPhotos!!.size}")
            Log.d("TAG", "limit: ${savedPhotos!!.size < limit}")
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
            authHeader = "Bearer N5_TpRGY647keBXA-Sc6FKkoqCt9J3KYyTl5leCO5z8",
            page = page
        )
        return response.body()?.let { Mappers.toSavedPhotoEntity(it) }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        Log.d("TAG", "getPageIndex: $pageIndex")
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
    }

}