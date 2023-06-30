package com.semenchuk.unsplash.di

import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.ui.home.HomeViewModel
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
import com.semenchuk.unsplash.ui.detailedPhoto.DetailedPhotosViewModel
import com.semenchuk.unsplash.ui.detailedPhoto.DetailedPhotosViewModelFactory
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
}

