package com.semenchuk.unsplash.domain

import android.util.Log
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.room.profile.SavedProfile
import com.semenchuk.unsplash.domain.utils.Mapper

class LoadUserProfileUseCase(private val unsplashRepository: UnsplashRepository) {

    suspend fun saveProfile() {
        val response = unsplashRepository.getUserProfile()
        Log.d("PROFILE", "saveProfile: ${response.body()}")
        unsplashRepository.saveUserProfile(Mapper.profileDtoToSavedProfile(response.body()!!))
    }

    suspend fun loadProfile(): SavedProfile {
        return unsplashRepository.loadUserProfile()
    }

    suspend fun userLogout(): Boolean {
        return unsplashRepository.logout()
    }
}