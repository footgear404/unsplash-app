package com.semenchuk.unsplash.domain.utils

sealed class State {
    object Loading: State()
    object Success: State()
    object Await: State()
    data class Error(val message: String): State()
}
