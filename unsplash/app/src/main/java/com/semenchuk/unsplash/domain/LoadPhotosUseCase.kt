package com.semenchuk.unsplash.domain

import androidx.paging.Pager
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

class LoadPhotosUseCase(
    private val unsplashRepository: UnsplashRepository
) {

    fun getPhotos(query: String): Pager<Int, SavedPhotoEntity> {
        return unsplashRepository.pagerForFeed(query)
    }
}