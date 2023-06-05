package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.entities.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
) : ViewModel() {

    var photos: Flow<PagingData<PhotoItem>> =
        loadPhotosUseCase.getPagingSource().flow.map { pagingData ->
            pagingData as PagingData<PhotoItem>
        }.cachedIn(viewModelScope)

    fun reloadPhotos() {
        viewModelScope.launch {
            loadPhotosUseCase.reloadPhotos()
        }
    }
}