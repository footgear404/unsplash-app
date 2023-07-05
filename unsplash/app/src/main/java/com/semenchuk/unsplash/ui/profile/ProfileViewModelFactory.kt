package com.semenchuk.unsplash.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase

class ProfileViewModelFactory(private val loadUserProfileUseCase: LoadUserProfileUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(loadUserProfileUseCase) as T
    }
}