package com.semenchuk.unsplash.data.retrofit

import com.semenchuk.unsplash.data.retrofit.models.UnsplashPhotosItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetPhotos {
    @GET("/photos")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 1
    ): Response<List<UnsplashPhotosItem>>
}