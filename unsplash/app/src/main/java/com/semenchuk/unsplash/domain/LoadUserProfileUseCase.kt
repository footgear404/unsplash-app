package com.semenchuk.unsplash.domain

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import com.semenchuk.unsplash.data.room.profile.SavedProfile
import com.semenchuk.unsplash.domain.utils.Mapper
import retrofit2.Response

class LoadUserProfileUseCase(private val unsplashRepository: UnsplashRepository) {

    suspend fun getProfile(): Response<ProfileDto> {
        val response = unsplashRepository.getUserProfile()
        unsplashRepository.saveProfile(Mapper.profileDtoToSavedProfile(response.body()!!))
        return response
    }

    suspend fun check(): List<SavedProfile> {
        return unsplashRepository.getFromDb()
    }
}