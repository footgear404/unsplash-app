package com.semenchuk.unsplash.data.retrofit.photoById.models

import android.os.Parcelable
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.Urls
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.User
import com.semenchuk.unsplash.entities.DetailedPhotoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class DetailedPhoto(
    @Json(name = "id")
    override val id: String,
    @Json(name = "downloads")
    override val downloads: Int,
    @Json(name = "likes")
    override val likes: Int,
    @Json(name = "liked_by_user")
    override val likedByUser: Boolean,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "exif")
    override val exif: Exif,
    @Json(name = "location")
    override val location: Location,
    @Json(name = "tags")
    override val tags: List<Tag>,
    @Json(name = "urls")
    override val urls: Urls,
    @Json(name = "user")
    override val user: User
) : DetailedPhotoItem, Parcelable