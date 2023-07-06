package com.semenchuk.unsplash.entities

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.Urls
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.User

interface PhotoEntity {
    val altDescription: String?
    val blurHash: String
    val color: String?
    val createdAt: String?
    val description: String?
    val height: Int?
    val id: String
    var likedByUser: Boolean
    var likes: Int?
    val promotedAt: String?
    val updatedAt: String?
    val urls: Urls?
    val user: User?
    val width: Int?
}