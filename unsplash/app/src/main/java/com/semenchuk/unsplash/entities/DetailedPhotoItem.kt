package com.semenchuk.unsplash.entities

import com.semenchuk.unsplash.data.retrofit.photoById.models.Exif
import com.semenchuk.unsplash.data.retrofit.photoById.models.Location
import com.semenchuk.unsplash.data.retrofit.photoById.models.Tag
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.Urls
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.User

interface DetailedPhotoItem {
    val id: String
    val downloads: Int
    val likes: Int
    val likedByUser: Boolean
    val description: String?
    val exif: Exif
    val location: Location?
    val tags: List<Tag>
    val urls: Urls
    val user: User
}