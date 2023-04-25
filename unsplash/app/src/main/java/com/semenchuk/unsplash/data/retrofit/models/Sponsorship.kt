package com.semenchuk.unsplash.data.retrofit.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sponsorship(
    @Json(name = "impression_urls")
    val impressionUrls: List<Any>?,
    @Json(name = "sponsor")
    val sponsor: Sponsor?,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "tagline_url")
    val taglineUrl: String?
)