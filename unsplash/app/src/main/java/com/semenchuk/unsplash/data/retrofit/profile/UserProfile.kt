package com.semenchuk.unsplash.data.retrofit.profile

import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserProfile {

    @GET("/me")
    suspend fun send(
        @Header("Authorization") authHeader: String
    ): Response<ProfileDto>
}