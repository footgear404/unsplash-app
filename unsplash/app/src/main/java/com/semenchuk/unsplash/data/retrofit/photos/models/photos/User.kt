package com.semenchuk.unsplash.data.retrofit.photos.models.photos


import android.os.Parcelable
import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "profile_image")
    @Embedded val profileImage: ProfileImage?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "description")
    val description: String?
): Parcelable