package com.semenchuk.unsplash.data.retrofit.photos

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetPhotos {
    @GET("/photos")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") orderBy: String = "latest"
    ): Response<List<UnsplashPhotosEntity>>
}