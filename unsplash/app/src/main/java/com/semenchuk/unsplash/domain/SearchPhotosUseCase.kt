package com.semenchuk.unsplash.domain

import com.semenchuk.unsplash.data.UnsplashRepository

class SearchPhotosUseCase(
    private val unsplashRepository: UnsplashRepository
) {

//    fun searchPhotos(query: String): Pager<Int, SavedPhotoEntity> {
//        return unsplashRepository.pagerForSearch(query)
//    }

}