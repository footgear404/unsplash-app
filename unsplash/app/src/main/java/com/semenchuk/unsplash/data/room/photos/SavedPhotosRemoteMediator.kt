package com.semenchuk.unsplash.data.room.photos

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.semenchuk.unsplash.AUTH_STATUS
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.domain.utils.Mappers

@ExperimentalPagingApi
class SavedPhotosRemoteMediator(
    val unsplashDatabaseDao: UnsplashDatabaseDao,
    private val retrofitService: RetrofitService,
    private val query: String
) : RemoteMediator<Int, SavedPhotoEntity>() {

    private val sp = App.appComponent.sharedPrefs()

    private var pageIndex = 0

    var savedPhotos: List<SavedPhotoEntity>? = emptyList()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, SavedPhotoEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize

        Log.d("TAG", "getPageIndex: $pageIndex")
//        Log.d("TAG", "limit: $limit")

        return try {
            savedPhotos = fetchPhotos(pageIndex, query)

            when (loadType) {
                LoadType.REFRESH -> {
                    unsplashDatabaseDao.refresh(savedPhotos!!)
                }
                LoadType.APPEND -> {
                    unsplashDatabaseDao.save(savedPhotos!!)
                }
                else -> {
                    Unit
                }
            }

//            Log.d("TAG", "size: ${savedPhotos!!.size}")
            MediatorResult.Success(endOfPaginationReached = savedPhotos!!.size < limit)
        } catch (e: Exception) {
//            Log.d("TAG", "load: $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchPhotos(page: Int, query: String): List<SavedPhotoEntity>? {
        Log.d("TAG", "fetchPhotos(query): $query")
        if (query.isEmpty()) {
            val response = retrofitService.getPhotos.send(
                authHeader = "Bearer ${sp.getString(AUTH_STATUS, null)}",
                page = page,
                per_page = PAGE_SIZE
            )
            return response.body()?.let { Mappers.toSavedPhotoEntity(it) }
        } else {
//            Log.d("TAG", "fetchPhotos: $query")
            val response = retrofitService.searchPhotos.send(
                authHeader = "Bearer ${sp.getString(AUTH_STATUS, null)}",
                query = query,
                page = page,
                per_page = PAGE_SIZE
            )
//            Log.d("TAG", "query: $response")
            return response.body()?.results.let { Mappers.toSavedPhotoEntity(it!!) }
        }
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

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 20
    }

}