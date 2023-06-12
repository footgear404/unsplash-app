package com.semenchuk.unsplash.entities

import com.semenchuk.unsplash.data.retrofit.photoById.models.Exif
import com.semenchuk.unsplash.data.retrofit.photoById.models.Location
import com.semenchuk.unsplash.data.retrofit.photoById.models.Tags
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.Urls

interface DetailedPhotoItem {
    val id: String
    val downloads: Int
    val likes: Int
    val likedByUser: Boolean
    val description: String?
    val exif: Exif
    val location: Location
    val tags: List<Tags>
    val urls: Urls
}