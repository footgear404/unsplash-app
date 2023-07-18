package com.semenchuk.unsplash.domain

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.collections.CollectionPagingSource
import com.semenchuk.unsplash.data.retrofit.collections.models.CollectionsItem
import retrofit2.Response

class LoadCollectionsUseCase(
    private val unsplashRepository: UnsplashRepository,
    val collectionPagingSource: CollectionPagingSource
) {

    suspend fun collections(page: Int): Response<List<CollectionsItem>> {
        return unsplashRepository.getCollections(page)
    }

}