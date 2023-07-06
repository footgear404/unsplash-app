package com.semenchuk.unsplash.di

import android.app.Application
import com.semenchuk.unsplash.domain.AuthUseCase
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase
import com.semenchuk.unsplash.ui.auth.AuthViewModel
import com.semenchuk.unsplash.ui.auth.AuthViewModelFactory
import com.semenchuk.unsplash.ui.detailedPhoto.DetailedPhotosViewModel
import com.semenchuk.unsplash.ui.detailedPhoto.DetailedPhotosViewModelFactory
import com.semenchuk.unsplash.ui.home.HomeViewModel
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
import com.semenchuk.unsplash.ui.profile.ProfileViewModel
import com.semenchuk.unsplash.ui.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UiModule {

    @Provides
    fun provideHomeViewModelFactory(
        loadPhotosUseCase: LoadPhotosUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(loadPhotosUseCase)
    }

    @Provides
    fun provideHomeViewModel(
        loadPhotosUseCase: LoadPhotosUseCase
    ): HomeViewModel {
        return HomeViewModel(loadPhotosUseCase)
    }

    @Provides
    fun provideDetailedViewModel(loadPhotosUseCase: LoadPhotosUseCase): DetailedPhotosViewModel {
        return DetailedPhotosViewModel(loadPhotosUseCase)
    }

    @Provides
    fun provideDetailedViewModelFactory(
        loadPhotosUseCase: LoadPhotosUseCase
    ): DetailedPhotosViewModelFactory {
        return DetailedPhotosViewModelFactory(loadPhotosUseCase)
    }

    @Provides
    fun provideProfileViewModel(loadUserProfileUseCase: LoadUserProfileUseCase): ProfileViewModel {
        return ProfileViewModel(loadUserProfileUseCase)
    }

    @Provides
    fun provideProfileViewModelFactory(
        loadUserProfileUseCase: LoadUserProfileUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(loadUserProfileUseCase)
    }

    @Provides
    fun provideAuthViewModel(
        application: Application,
        authUseCase: AuthUseCase
    ): AuthViewModel {
        return AuthViewModel(application, authUseCase)
    }

    @Provides
    fun provideAuthViewModelFactory(
        application: Application,
        authUseCase: AuthUseCase
    ): AuthViewModelFactory {
        return AuthViewModelFactory(application, authUseCase)
    }

}

