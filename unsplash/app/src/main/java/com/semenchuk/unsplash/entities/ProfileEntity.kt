package com.semenchuk.unsplash.entities

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.ProfileImage
import com.semenchuk.unsplash.data.retrofit.profile.models.UserLinks

interface ProfileEntity {
    val id: String
    val bio: String?
    val downloads: Int?
    val email: String?
    val firstName: String?
    val followedByUser: Boolean?
    val instagramUsername: String?
    val lastName: String?
    val links: UserLinks
    val location: String?
    val portfolioUrl: String?
    val totalCollections: Int?
    val totalLikes: Int?
    val totalPhotos: Int?
    val twitterUsername: String?
    val updatedAt: String?
    val uploadsRemaining: Int?
    val username: String?
    val profileImage: ProfileImage?
}