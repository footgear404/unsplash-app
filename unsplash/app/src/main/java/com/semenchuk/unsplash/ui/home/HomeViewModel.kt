package com.semenchuk.unsplash.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.semenchuk.unsplash.data.retrofit.like.models.Photo
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.domain.utils.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase
) : ViewModel() {

    private var _photos: Flow<PagingData<SavedPhotoEntity>>? = null
    val photos get() = _photos!!

    private var _message = Channel<String>(Channel.BUFFERED)
    val message get() = _message.receiveAsFlow()

    private var _likeUnlike = Channel<Pair<Int, Photo?>>(Channel.BUFFERED)
    val likeUnlike get() = _likeUnlike.receiveAsFlow()

    private var _state = MutableStateFlow<State>(State.Await)
    val state get() = _state.asStateFlow()

    init {
        load()
    }

    fun load(query: String = "") {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                _photos =
                    loadPhotosUseCase.getPhotos(query = query).flow
//                .map { pagingData -> pagingData as PagingData<PhotoItem> }
                _state.value = State.Success
            } catch (e: java.lang.Exception) {
                _state.value = State.Error(message = e.message.toString())
            }
        }
    }

    fun setLike(id: String, isLiked: Boolean, position: Int) {
        viewModelScope.launch {
            if (isLiked) {
                val result = loadPhotosUseCase.unlike(id, isLiked).body()
                Log.d("LIKE", "$id is liked: ${result?.photo?.likedByUser}")
                _likeUnlike.send(Pair(position, result?.photo))
            } else {
                val result = loadPhotosUseCase.like(id, isLiked).body()
                Log.d("LIKE", "$id is liked: ${result?.photo?.likedByUser}")
                _likeUnlike.send(Pair(position, result?.photo))
            }
        }
    }

    fun sendMessageInSnack(message: String) {
        viewModelScope.launch {
            _message.send(message)
        }
    }
}
