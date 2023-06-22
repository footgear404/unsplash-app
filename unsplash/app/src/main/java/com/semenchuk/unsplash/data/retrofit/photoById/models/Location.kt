package com.semenchuk.unsplash.data.retrofit.photoById.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "position")
    val position: Position?
): Parcelable
