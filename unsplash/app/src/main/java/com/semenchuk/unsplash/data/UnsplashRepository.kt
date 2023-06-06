package com.semenchuk.unsplash.data

import androidx.paging.*
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.room.UnsplashDatabaseDao
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

@OptIn(ExperimentalPagingApi::class)
class UnsplashRepository(
    private val unsplashDatabaseDao: UnsplashDatabaseDao,
    private val retrofitService: RetrofitService
) {

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
}