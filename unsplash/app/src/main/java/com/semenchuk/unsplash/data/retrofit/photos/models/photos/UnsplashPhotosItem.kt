package com.semenchuk.unsplash.data.retrofit.photos.models.photos


import com.semenchuk.unsplash.entities.PhotoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotosItem(
    @Json(name = "alt_description")
    override val altDescription: String?,
    @Json(name = "blur_hash")
    override val blurHash: String?,
    @Json(name = "color")
    override val color: String?,
    @Json(name = "created_at")
    override val createdAt: String?,
    @Json(name = "current_user_collections")
    override val currentUserCollections: List<Any>?,
    @Json(name = "description")
    override val description: Any?,
    @Json(name = "height")
    override val height: Int?,
    @Json(name = "id")
    override val id: String?,
    @Json(name = "liked_by_user")
    override val likedByUser: Boolean?,
    @Json(name = "likes")
    override val likes: Int?,
    @Json(name = "links")
    override val links: Links?,
    @Json(name = "promoted_at")
    override val promotedAt: String?,
    @Json(name = "sponsorship")
    override val sponsorship: Sponsorship?,
    @Json(name = "topic_submissions")
    override val topicSubmissions: TopicSubmissions?,
    @Json(name = "updated_at")
    override val updatedAt: String?,
    @Json(name = "urls")
    override val urls: Urls?,
    @Json(name = "user")
    override val user: User?,
    @Json(name = "width")
    override val width: Int?
): PhotoItem