package com.semenchuk.unsplash.di

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.appAuth.AuthRepository
import com.semenchuk.unsplash.domain.AuthUseCase
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService

@Module
class DomainModule {
    @Provides
    fun provideLoadPhotosUseCase(
        unsplashRepository: UnsplashRepository
    ): LoadPhotosUseCase {
        return LoadPhotosUseCase(unsplashRepository)
    }

    @Provides
    fun provideLoadUserProfileUseCase(unsplashRepository: UnsplashRepository): LoadUserProfileUseCase {
        return LoadUserProfileUseCase(unsplashRepository)
    }

    @Provides
    fun provideAuthUseCase(
        authRepository: AuthRepository,
        authService: AuthorizationService
    ): AuthUseCase {
        return AuthUseCase(authRepository, authService)
    }
}