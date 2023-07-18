package com.semenchuk.unsplash.data.retrofit.collections

import com.semenchuk.unsplash.data.retrofit.collections.models.CollectionsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CollectionList {

    @GET("/collections")
    suspend fun send(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
    ): Response<List<CollectionsItem>>
}