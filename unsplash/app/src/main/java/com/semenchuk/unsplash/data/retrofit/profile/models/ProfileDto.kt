package com.semenchuk.unsplash.data.retrofit.profile.models


import com.semenchuk.unsplash.data.retrofit.photos.models.photos.ProfileImage
import com.semenchuk.unsplash.entities.ProfileEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileDto(
    @Json(name = "id")
    override val id: String,
    @Json(name = "bio")
    override val bio: String?,
    @Json(name = "downloads")
    override val downloads: Int?,
    @Json(name = "email")
    override val email: String?,
    @Json(name = "first_name")
    override val firstName: String?,
    @Json(name = "followed_by_user")
    override val followedByUser: Boolean?,
    @Json(name = "instagram_username")
    override val instagramUsername: String?,
    @Json(name = "last_name")
    override val lastName: String?,
    @Json(name = "links")
    override val links: UserLinks,
    @Json(name = "location")
    override val location: String?,
    @Json(name = "portfolio_url")
    override val portfolioUrl: String?,
    @Json(name = "total_collections")
    override val totalCollections: Int?,
    @Json(name = "total_likes")
    override val totalLikes: Int?,
    @Json(name = "total_photos")
    override val totalPhotos: Int?,
    @Json(name = "twitter_username")
    override val twitterUsername: String?,
    @Json(name = "updated_at")
    override val updatedAt: String?,
    @Json(name = "uploads_remaining")
    override val uploadsRemaining: Int?,
    @Json(name = "username")
    override val username: String?,
    @Json(name = "profile_image")
    override val profileImage: ProfileImage?,
) : ProfileEntity