package com.semenchuk.unsplash.data

import androidx.paging.*

@OptIn(ExperimentalPagingApi::class)
class UnsplashRepository(
    private val savedPhotosRemoteMediator: SavedPhotosRemoteMediator
) {
    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = savedPhotosRemoteMediator,
        initialKey = null,
        pagingSourceFactory = {
            savedPhotosRemoteMediator.unsplashDatabaseDao.getPhotosPagingSource()
        }
    )

}