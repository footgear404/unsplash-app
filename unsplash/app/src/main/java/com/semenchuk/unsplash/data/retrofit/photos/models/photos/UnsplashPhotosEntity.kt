package com.semenchuk.unsplash.data.retrofit.photos.models.photos


import android.os.Parcelable
import com.semenchuk.unsplash.entities.PhotoEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UnsplashPhotosEntity(
    @Json(name = "alt_description")
    override val altDescription: String?,
    @Json(name = "blur_hash")
    override val blurHash: String,
    @Json(name = "color")
    override val color: String?,
    @Json(name = "created_at")
    override val createdAt: String?,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "height")
    override val height: Int?,
    @Json(name = "id")
    override val id: String,
    @Json(name = "liked_by_user")
    override var likedByUser: Boolean,
    @Json(name = "likes")
    override var likes: Int?,
    @Json(name = "promoted_at")
    override val promotedAt: String?,
    @Json(name = "updated_at")
    override val updatedAt: String?,
    @Json(name = "urls")
    override val urls: Urls?,
    @Json(name = "user")
    override val user: User?,
    @Json(name = "width")
    override val width: Int?
) : PhotoEntity, Parcelable