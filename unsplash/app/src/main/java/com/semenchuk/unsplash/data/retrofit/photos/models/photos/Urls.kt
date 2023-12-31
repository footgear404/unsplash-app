package com.semenchuk.unsplash.data.retrofit.photos.models.photos


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "full")
    val full: String?,
    @Json(name = "raw")
    val raw: String?,
    @Json(name = "regular")
    val regular: String,
    @Json(name = "small_s3")
    val smallS3: String?,
    @Json(name = "thumb")
    val thumb: String?
): Parcelable