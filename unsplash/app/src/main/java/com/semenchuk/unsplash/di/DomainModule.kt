package com.semenchuk.unsplash.di

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideLoadPhotosUseCase(
        unsplashRepository: UnsplashRepository
    ): LoadPhotosUseCase {
        return LoadPhotosUseCase(unsplashRepository)
    }
}