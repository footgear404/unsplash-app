package com.semenchuk.unsplash.ui.singlePhoto

import android.graphics.Bitmap
import android.os.Environment
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailedPhotosViewModel(
    private val loadPhotosUseCase: LoadPhotosUseCase,
) : ViewModel() {

    private var _photo = MutableStateFlow<DetailedPhoto?>(null)
    val photo get() = _photo.asStateFlow()

    private var _state = MutableStateFlow<State>(State.Await)
    val state get() = _state.asStateFlow()

    private var _message = Channel<String>(Channel.BUFFERED)
    val message get() = _message.receiveAsFlow()

    fun load(id: String) {
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

    fun download(image: Bitmap, fileName: String): String? {
        var savedImagePath: String? = null
        val imageFileName = "$fileName.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/unsplash"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            sendMessage("Image was saved in $savedImagePath")
        }
        return savedImagePath
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _message.send(message)
        }
    }


}