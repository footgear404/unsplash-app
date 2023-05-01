package com.semenchuk.unsplash.data

import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import retrofit2.Response

class UnsplashRepository(
    private val retrofitService: RetrofitService
) {
    suspend fun getPhotos(
        token: String,
        page: Int,
        per_page: Int
    ): Response<List<UnsplashPhotosItem>> {
        return retrofitService.getPhotos.send(
            authHeader = token,
            page = page,
            per_page = per_page
        )
    }

}