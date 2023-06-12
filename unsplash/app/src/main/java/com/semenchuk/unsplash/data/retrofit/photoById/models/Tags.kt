package com.semenchuk.unsplash.data.retrofit.photoById.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tags(
    @Json(name = "title")
    val title: String
)
