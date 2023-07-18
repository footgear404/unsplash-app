package com.semenchuk.unsplash.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semenchuk.unsplash.domain.LoadCollectionsUseCase

class CollectionViewModelFactory(private val loadCollectionsUseCase: LoadCollectionsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CollectionsViewModel(loadCollectionsUseCase) as T
    }
}