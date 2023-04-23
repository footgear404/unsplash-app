package com.semenchuk.unsplash.data.auth.models

data class TokensModel (
    val access_token: String,
    val token_type: String,
    val scope: String,
)