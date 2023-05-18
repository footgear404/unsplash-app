package com.semenchuk.unsplash.data.retrofit.photos

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetPhotos {
    @GET("/photos")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = PAGE_SIZE,
        @Query("order_by") orderBy: String = "popular"
    ): Response<List<UnsplashPhotosItem>>

    companion object {
        const val PAGE_SIZE = 20
    }
}