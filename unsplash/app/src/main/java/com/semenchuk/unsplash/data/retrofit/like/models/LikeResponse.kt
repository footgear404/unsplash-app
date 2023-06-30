package com.semenchuk.unsplash.data.retrofit.like.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikeResponse(
    @Json(name = "photo")
    val photo: Photo?,
)