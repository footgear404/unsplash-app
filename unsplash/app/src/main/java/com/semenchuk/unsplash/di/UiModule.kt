package com.semenchuk.unsplash.di

import android.app.Application
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.ui.home.HomeViewModel
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
import com.semenchuk.unsplash.ui.home.paged_adapter.UnsplashPagedAdapter
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
    fun provideUnsplashPagedAdapter(): UnsplashPagedAdapter {
        return UnsplashPagedAdapter()
    }
}

