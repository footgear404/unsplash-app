package com.semenchuk.unsplash.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.semenchuk.unsplash.data.retrofit.collections.models.CollectionsItem
import com.semenchuk.unsplash.domain.LoadCollectionsUseCase
import kotlinx.coroutines.flow.Flow

class CollectionsViewModel(
    private val loadCollectionsUseCase: LoadCollectionsUseCase,
) : ViewModel() {

    val pagingCharacters: Flow<PagingData<CollectionsItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = null,
        pagingSourceFactory = { loadCollectionsUseCase.collectionPagingSource }
    ).flow.cachedIn(viewModelScope)

//    fun loadCollections() {
//        viewModelScope.launch {
//            try {
//                val res = loadCollectionsUseCase.collections()
//                Log.d("COLLECTIONS", "loadCollections: ${res.body()}")
//            } catch (e: Exception) {
//                Log.d("COLLECTIONS", "loadCollections: $e")
//            }
//        }
//    }
}