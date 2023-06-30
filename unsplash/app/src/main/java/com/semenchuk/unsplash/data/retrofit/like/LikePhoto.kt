package com.semenchuk.unsplash.data.retrofit.like

import com.semenchuk.unsplash.data.retrofit.like.models.LikeResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LikePhoto {
    @POST("/photos/{id}/like")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<LikeResponse>
}