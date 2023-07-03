package com.semenchuk.unsplash.ui.detailedPhoto

import android.graphics.Bitmap
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semenchuk.unsplash.data.retrofit.like.models.Photo
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

    private var _likeUnlike = Channel<Photo?>(Channel.BUFFERED)
    val likeUnlike get() = _likeUnlike.receiveAsFlow()

    private var _message = Channel<String>(Channel.BUFFERED)
    val message get() = _message.receiveAsFlow()


    fun load(id: String) {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                val result = loadPhotosUseCase.findPhoto(id = id)
                if (result.isSuccessful) {
                    _photo.value = result.body()!!
                    _state.value = State.Success
                } else {
                    _state.value = State.Error("Else: " + result.message())
                }
            } catch (e: Exception) {
                Log.d("TAG", "findPhoto catch: ${e.message}")
                _message.send(e.message.toString())
            }
        }
    }

    fun saveImage(image: Bitmap?, imageName: String): String? {
        if (image != null) {
            var savedImagePath: String? = null
            val imageFileName = "$imageName.$FILE_FORMAT"
            val storageDir = File(
                "${getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/$FOLDER_NAME"
            )
            var success = true
            if (!storageDir.exists()) {
                success = storageDir.mkdirs()
            }
            if (success) {
                val imageFile = File(storageDir, imageFileName)
                savedImagePath = imageFile.absolutePath
                try {
                    val fileOut: OutputStream = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fileOut)
                    fileOut.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return savedImagePath
        } else {
            Log.d("DOWNLOAD", "image is null")
            return null
        }
    }

    fun setLike(id: String, isLiked: Boolean) {
        viewModelScope.launch {
            if (isLiked) {
                val result = loadPhotosUseCase.unlike(id, isLiked).body()
                Log.d("LIKE", "$id is liked: ${result?.photo?.likedByUser}")
                _likeUnlike.send(result?.photo)
            } else {
                val result = loadPhotosUseCase.like(id, isLiked).body()
                Log.d("LIKE", "$id is liked: ${result?.photo?.likedByUser}")
                _likeUnlike.send(result?.photo)
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _message.send(message)
        }
    }

    companion object {
        const val FOLDER_NAME = "unsplash"
        const val FILE_FORMAT = "jpg"
    }

}