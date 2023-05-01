package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import com.semenchuk.unsplash.domain.LoadPhotosUseCase

class HomeViewModelFactory(
    private val loadPhotosUseCase: LoadPhotosUseCase,
    private val photosPagingSource: PhotosPagingSource
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(loadPhotosUseCase, photosPagingSource) as T
    }
}