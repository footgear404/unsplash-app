package com.semenchuk.unsplash.data.retrofit.photoById

import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PhotoById {
    @GET("/photos/{id}")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<DetailedPhoto>
}