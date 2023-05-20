package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.domain.LoadPhotosUseCase

class HomeViewModelFactory(
    private val loadPhotosUseCase: LoadPhotosUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(loadPhotosUseCase) as T
    }
}