package com.semenchuk.unsplash.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase
import com.semenchuk.unsplash.domain.utils.Mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val loadUserProfileUseCase: LoadUserProfileUseCase) : ViewModel() {

    private var _profile = MutableStateFlow<ProfileDto?>(null)
    val profile get() = _profile.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            try {
                val result = loadUserProfileUseCase.loadProfile()
                _profile.value = Mapper.savedProfileToProfileDto(result)
                Log.d("DB", "getProfile: $result")
            } catch (e: Exception) {
                Log.d("PROFILE", "ERROR: $e")
            }
        }
    }

}