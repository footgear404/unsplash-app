package com.semenchuk.unsplash.data.retrofit.profile.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserLinks(
    @Json(name = "html")
    val html: String?,
    @Json(name = "likes")
    val likes: String?,
    @Json(name = "photos")
    val photos: String?,
    @Json(name = "portfolio")
    val portfolio: String?,
    @Json(name = "self")
    val self: String?
)