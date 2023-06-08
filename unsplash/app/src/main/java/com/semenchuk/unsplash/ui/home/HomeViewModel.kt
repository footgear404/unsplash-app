package com.semenchuk.unsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.domain.utils.State
import com.semenchuk.unsplash.entities.PhotoItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(private val loadPhotosUseCase: LoadPhotosUseCase) : ViewModel() {

    private val query = MutableStateFlow("")

    private var _photos: Flow<PagingData<PhotoItem>>? = null
    val photos get() = _photos

    private var _message = Channel<String>(Channel.BUFFERED)
    val message get() = _message.receiveAsFlow()

    private var _state = MutableStateFlow<State>(State.Await)
    val state get() = _state.asStateFlow()

    init {
        load()
    }

    fun load(query: String = "") {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                _photos = loadPhotosUseCase.getPhotos(query = query).flow.map { pagingData ->
                    pagingData as PagingData<PhotoItem>
                }
                _state.value = State.Success
            } catch (e: java.lang.Exception) {
                _state.value = State.Error(message = e.message.toString())
            }
        }
    }

    fun sendMessageInSnack(message: String) {
        viewModelScope.launch {
            _message.send(message)
        }
    }

    fun searchPhotos(query: String) {
        this.query.value = query
    }

//        _state.value = State.Loading
//        viewModelScope.launch {
//            try {
//                _photos = this@HomeViewModel.query.flatMapLatest { query ->
//                    loadPhotosUseCase.getPhotos(query = query).flow.map { pagingData ->
//                        pagingData as PagingData<PhotoItem>
//                    }.cachedIn(viewModelScope)
//                }
//                _state.value = State.Success
//            } catch (e: java.lang.Exception) {
//                _state.value = State.Error(message = e.message.toString())
//            }
//        }

}
