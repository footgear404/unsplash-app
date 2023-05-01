package com.semenchuk.unsplash.domain

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem

class LoadPhotosUseCase(
    private val unsplashRepository: UnsplashRepository
) {
    suspend fun execute(
        token: String,
        page: Int,
        per_page: Int
    ): List<UnsplashPhotosItem> {
        return unsplashRepository.getPhotos(token, page, per_page).body()!!
    }
}