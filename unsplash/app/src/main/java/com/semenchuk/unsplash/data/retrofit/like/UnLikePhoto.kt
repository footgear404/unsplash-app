package com.semenchuk.unsplash.data.retrofit.like

import com.semenchuk.unsplash.data.retrofit.like.models.LikeResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface UnLikePhoto {
    @DELETE("/photos/{id}/like")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<LikeResponse>
}