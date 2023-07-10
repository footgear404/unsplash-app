package com.semenchuk.unsplash.domain

import android.app.Application
import com.semenchuk.unsplash.data.appAuth.AuthRepository
import net.openid.appauth.AuthorizationService

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService
) {

    fun getAuthRepository() = authRepository
    fun getAuthService(application: Application) = authService



}