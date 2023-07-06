package com.semenchuk.unsplash.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase
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
//                val result = loadUserProfileUseCase.getProfile()
//                val result = loadUserProfileUseCase.getProfile()
                Log.d("DB", "getProfile: ${loadUserProfileUseCase.check()}")
//                if (result.isSuccessful) {
//                    _profile.value = result.body()
//                } else {
//                    Log.d("PROFILE", "getProfile: ${result.message()}")
//                }
            } catch (e: Exception) {
                Log.d("PROFILE", "getProfile: $e")
            }
        }
    }

}