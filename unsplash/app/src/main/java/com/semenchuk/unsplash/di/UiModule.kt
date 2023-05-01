package com.semenchuk.unsplash.di

import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.ui.home.HomeViewModel
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UiModule {


    @Provides
    fun provideHomeViewModelFactory(
        loadPhotosUseCase: LoadPhotosUseCase,
        photosPagingSource: PhotosPagingSource
    ): HomeViewModelFactory {
        return HomeViewModelFactory(loadPhotosUseCase, photosPagingSource)
    }

    @Provides
    fun provideHomeViewModel(
        loadPhotosUseCase: LoadPhotosUseCase,
        photosPagingSource: PhotosPagingSource
    ): HomeViewModel {
        return HomeViewModel(loadPhotosUseCase, photosPagingSource)
    }

}