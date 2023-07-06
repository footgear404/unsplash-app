package com.semenchuk.unsplash.data.room.profile

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.ProfileImage
import com.semenchuk.unsplash.data.retrofit.profile.models.UserLinks
import com.semenchuk.unsplash.entities.ProfileEntity

@Entity(tableName = "saved_profile")
data class SavedProfile(
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: String,
    @ColumnInfo(name = "bio")
    override val bio: String?,
    @ColumnInfo(name = "downloads")
    override val downloads: Int?,
    @ColumnInfo(name = "email")
    override val email: String?,
    @ColumnInfo(name = "first_name")
    override val firstName: String?,
    @ColumnInfo(name = "followed_by_user")
    override val followedByUser: Boolean?,
    @ColumnInfo(name = "instagram_username")
    override val instagramUsername: String?,
    @ColumnInfo(name = "last_name")
    override val lastName: String?,
    @Embedded
    override val links: UserLinks,
    @ColumnInfo(name = "location")
    override val location: String?,
    @ColumnInfo(name = "portfolio_url")
    override val portfolioUrl: String?,
    @ColumnInfo(name = "total_collections")
    override val totalCollections: Int?,
    @ColumnInfo(name = "total_likes")
    override val totalLikes: Int?,
    @ColumnInfo(name = "total_photos")
    override val totalPhotos: Int?,
    @ColumnInfo(name = "twitter_username")
    override val twitterUsername: String?,
    override val updatedAt: String?,
    override val uploadsRemaining: Int?,
    @ColumnInfo(name = "username")
    override val username: String?,
    @Embedded
    override val profileImage: ProfileImage?
) : ProfileEntity
