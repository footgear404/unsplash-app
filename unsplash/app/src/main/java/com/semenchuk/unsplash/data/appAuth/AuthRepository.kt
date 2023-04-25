package com.semenchuk.unsplash.data.appAuth

import android.util.Log
import com.semenchuk.unsplash.data.appAuth.models.TokensModel
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
    ): TokensModel {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.access_token = tokens.access_token
        TokenStorage.token_type = tokens.token_type
        TokenStorage.scope = tokens.scope
        Log.d("TAG", "performTokenRequest: Tokens accepted: ${tokens.access_token}")
        return tokens
    }
}