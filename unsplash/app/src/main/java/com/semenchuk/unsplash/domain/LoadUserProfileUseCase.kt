package com.semenchuk.unsplash.domain

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.profile.models.Me
import retrofit2.Response

class LoadUserProfileUseCase(private val unsplashRepository: UnsplashRepository) {

    suspend fun getProfile(): Response<Me> {
        return unsplashRepository.getUserProfile()
    }
}