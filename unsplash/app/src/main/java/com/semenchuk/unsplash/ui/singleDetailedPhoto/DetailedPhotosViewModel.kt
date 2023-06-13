package com.semenchuk.unsplash.ui.singleDetailedPhoto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import com.semenchuk.unsplash.domain.LoadPhotosUseCase
import com.semenchuk.unsplash.domain.utils.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailedPhotosViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
) : ViewModel() {

    private var _photo = MutableStateFlow<DetailedPhoto?>(null)
    val photo get() = _photo.asStateFlow()

    private var _state = MutableStateFlow<State>(State.Await)
    val state get() = _state.asStateFlow()

    private var _message = Channel<String>(Channel.BUFFERED)
    val message get() = _message.receiveAsFlow()

    init {
        load("V8w0gSmxajY")
    }

    private fun load(id: String) {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                val result = loadPhotosUseCase.findPhoto(id = id)
                Log.d("TAG", "try load: ${result.message()}")
                if (result.isSuccessful) {
                    _photo.value = result.body()!!
                    _state.value = State.Success
                    _message.send("Load success!")
                    Log.d("TAG", "if load: ${result.message()}")
                } else {
                    Log.d("TAG", "else load: ${result.message()}")
                    _state.value = State.Error(result.message())
                }
            } catch (e: Exception) {
                Log.d("TAG", "load: ${e.message}")
                _message.send(e.message.toString())
            }
        }
    }

    suspend fun sendMessage(message: String) {
        _message.send(message)
    }


}