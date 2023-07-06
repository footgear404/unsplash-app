package com.semenchuk.unsplash.ui.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.domain.AuthUseCase
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase

class AuthViewModelFactory(
    private val application: Application,
    private val authUseCase: AuthUseCase,
    private val loadUserProfileUseCase: LoadUserProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(application, authUseCase, loadUserProfileUseCase) as T
    }
}