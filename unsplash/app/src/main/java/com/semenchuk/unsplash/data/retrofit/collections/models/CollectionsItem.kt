package com.semenchuk.unsplash.data.retrofit.collections.models


import com.semenchuk.unsplash.data.retrofit.photos.models.photos.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionsItem(
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "private")
    val `private`: Boolean?,
    @Json(name = "published_at")
    val publishedAt: String?,
    @Json(name = "share_key")
    val shareKey: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "total_photos")
    val totalPhotos: Int?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "user")
    val user: User?
)