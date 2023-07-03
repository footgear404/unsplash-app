package com.semenchuk.unsplash.domain

import androidx.paging.Pager
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.like.models.LikeResponse
import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import retrofit2.Response

class LoadPhotosUseCase(
    private val unsplashRepository: UnsplashRepository
) {

    fun getPhotos(query: String): Pager<Int, SavedPhotoEntity> {
        return unsplashRepository.pagerForFeed(query)
    }

    suspend fun findPhoto(id: String): Response<DetailedPhoto> {
        return unsplashRepository.getPhotoById(id)
    }

    suspend fun like(id: String, likedByUser: Boolean): Response<LikeResponse> {
        return unsplashRepository.addLike(id, likedByUser)
    }

    suspend fun unlike(id: String, likedByUser: Boolean): Response<LikeResponse> {
        return unsplashRepository.removeLike(id, likedByUser)
    }
}