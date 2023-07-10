package com.semenchuk.unsplash.data.appAuth

import android.util.Log
import com.semenchuk.unsplash.app.App
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest

class AuthRepository {

    fun corruptAccessToken() {
        TokenStorage.access_token = "fake token"
    }

    fun logout() {
        TokenStorage.access_token = null
        TokenStorage.token_type = null
        TokenStorage.scope = null
    }

    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return AppAuth.getEndSessionRequest()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val token = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //perform success, save token and compete auth
        with(TokenStorage) {
            this.access_token = token.access_token
            this.token_type = token.token_type
            this.scope = token.scope
        }
        Log.d(
            "TOKEN_STORAGE",
            "TokenStorage: ${TokenStorage.access_token} | ${TokenStorage.token_type} | ${TokenStorage.scope}"
        )
        saveToken(token = TokenStorage)
    }

    private fun saveToken(token: TokenStorage) {
        val sp = App.appComponent.sharedPrefs()
        val tokenSharedPrefs = sp.edit()
        tokenSharedPrefs.putString(ACCESS_TOKEN, token.access_token)
        tokenSharedPrefs.putString(TOKEN_TYPE, token.token_type)
        tokenSharedPrefs.putString(TOKEN_SCOPE, token.scope)
        tokenSharedPrefs.apply()
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val TOKEN_TYPE = "TOKEN_TYPE"
        const val TOKEN_SCOPE = "TOKEN_SCOPE"
    }
}