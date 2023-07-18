package com.semenchuk.unsplash.ui.auth

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.domain.AuthUseCase
import com.semenchuk.unsplash.domain.LoadUserProfileUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthViewModel(
    application: Application,
    authUseCase: AuthUseCase,
    private val loadUserProfileUseCase: LoadUserProfileUseCase
) : AndroidViewModel(application) {

    private val authRepository = authUseCase.getAuthRepository()
    private val authService: AuthorizationService = authUseCase.getAuthService()

    private val _openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    val openAuthPageEventChannel get() = _openAuthPageEventChannel.receiveAsFlow()

    private val _toastEventChannel = Channel<Int>(Channel.BUFFERED)
    val toastEventChannel get() = _toastEventChannel.receiveAsFlow()

    private val _authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)
    val authSuccessEventChannel get() = _authSuccessEventChannel.receiveAsFlow()

    private val _loadingMutableStateFlow = MutableStateFlow(false)
    val loadingMutableStateFlow get() = _loadingMutableStateFlow.asStateFlow()

    private var _status = MutableStateFlow(false)
    val status get() = _status.asStateFlow()


    fun onAuthCodeFailed(exception: AuthorizationException) {
        _toastEventChannel.trySendBlocking(R.string.auth_canceled)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {

        Log.d("TAG", "trySendBlocking: open: ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            _loadingMutableStateFlow.value = true
            runCatching {
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                _loadingMutableStateFlow.value = false
                _authSuccessEventChannel.send(Unit)
            }.onFailure {
                _loadingMutableStateFlow.value = false
                _toastEventChannel.send(R.string.auth_canceled)
            }
        }
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = authRepository.getAuthRequest()

        Log.d("TAG", "openLoginPage (codeVerifier): ${authRequest.codeVerifier}")

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
        _openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)

        Log.d("TAG", "openLoginPage (authRequest): ${authRequest.toUri()}")
    }

    fun saveProfileToDb() {
        viewModelScope.launch {
            try {
                loadUserProfileUseCase.saveProfile()
                _status.value = true
            } catch (e: Exception) {
                Log.d("PROFILE", "getProfile: $e")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}