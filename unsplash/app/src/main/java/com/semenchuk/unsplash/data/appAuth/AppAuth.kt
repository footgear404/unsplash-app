package com.semenchuk.unsplash.data.appAuth

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.semenchuk.unsplash.data.appAuth.models.TokensModel
import net.openid.appauth.*
import kotlin.coroutines.suspendCoroutine

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI),
        null,
        Uri.parse(AuthConfig.END_SESSION_URI)
    )

    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = AuthConfig.CALLBACK_URL.toUri()

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return EndSessionRequest.Builder(serviceConfiguration)
            .setPostLogoutRedirectUri(AuthConfig.LOGOUT_CALLBACK_URL.toUri())
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(AuthConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication()
            ) { response, ex ->
                when {
                    response != null -> {
                        Log.d("TAG", "performTokenRequestSuspend: $response")
                        val tokens = TokensModel(
                            access_token = response.accessToken.orEmpty(),
                            token_type = response.tokenType.orEmpty(),
                            scope = response.scope.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                        Log.d("TAG", "performTokenRequestSuspend: ${ex.errorDescription}")

                    }
                    else -> error("unreachable")
                }
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }

    private object AuthConfig {
        const val AUTH_URI = "https://unsplash.com/oauth/authorize"
        const val TOKEN_URI = "https://unsplash.com/oauth/token"
        const val END_SESSION_URI = "https://unsplash.com/logout"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE =
            "public read_user write_user read_photos write_photos write_likes write_followers read_collections write_collections"

        const val CLIENT_ID = "qaKtejOGHbh1J4vsYULaVxQphZNW_HRUuW3hz7q9zmo"
        const val CLIENT_SECRET = "aq_C_wEqaOcrNwlx0AXVEkx3dn1dYtaMHpTqztaS-UE"
        const val CALLBACK_URL = "com.semenchuk.unsplash://unsplash"
        const val LOGOUT_CALLBACK_URL = ""
    }
}