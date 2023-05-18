package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.entities.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
    private val photosPagingSource: PhotosPagingSource
) : ViewModel() {

    private val _allPhotos = MutableStateFlow<PagingData<PhotoItem>?>(null)
    val allPhotos get() = _allPhotos.asStateFlow()

    val photos: Flow<PagingData<PhotoItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = null,
        pagingSourceFactory = { photosPagingSource }
    ).flow.cachedIn(viewModelScope)

    fun getPhotos() {
        viewModelScope.launch {
        }
    }

    fun searchPhotos() {

    }
}