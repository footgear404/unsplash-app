package com.semenchuk.unsplash.ui.singlePhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.domain.LoadPhotosUseCase

class DetailedPhotosViewModelFactory(
    private val loadPhotosUseCase: LoadPhotosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailedPhotosViewModel(loadPhotosUseCase) as T
    }
}