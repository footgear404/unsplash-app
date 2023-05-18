package com.semenchuk.unsplash.data.retrofit.photos.models.photos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Social(
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "paypal_email")
    val paypalEmail: String?,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "twitter_username")
    val twitterUsername: String?
)