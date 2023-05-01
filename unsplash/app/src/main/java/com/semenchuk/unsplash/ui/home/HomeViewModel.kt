package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
    private val photosPagingSource: PhotosPagingSource
) : ViewModel() {

    init {
//        loadPhotosUseCase.execute()
    }

    val photos: Flow<PagingData<UnsplashPhotosItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = null,
        pagingSourceFactory = { photosPagingSource }
    ).flow.cachedIn(viewModelScope)
}