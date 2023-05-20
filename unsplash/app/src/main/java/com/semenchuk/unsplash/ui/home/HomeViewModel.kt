package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.entities.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
) : ViewModel() {

    private val _allPhotos = MutableStateFlow<PagingData<PhotoItem>?>(null)
    val allPhotos get() = _allPhotos.asStateFlow()

    val photos: Flow<PagingData<PhotoItem>> =
        loadPhotosUseCase.getPagingSource().flow.map { pagingData ->
            pagingData.map {
                it as PhotoItem
            }
        }.cachedIn(viewModelScope)

}