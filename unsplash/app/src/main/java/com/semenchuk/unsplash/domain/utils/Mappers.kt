package com.semenchuk.unsplash.domain.utils

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

class Mappers {
    companion object {
        fun toSavedPhotoEntity(unsplashPhotosItem: List<UnsplashPhotosItem>): List<SavedPhotoEntity> {
            val savedPhotoEntityList = mutableListOf<SavedPhotoEntity>()
            unsplashPhotosItem.forEach {
                savedPhotoEntityList.add(
                    SavedPhotoEntity(
                        id = it.id ?: "Nothing",
                        background_photo = "${it.urls?.regular}",
                        author_photo = "${it.user?.profileImage?.medium}",
                        author_name = "${it.user?.firstName} ${it.user?.lastName}",
                        nickname = "${it.user?.username}",
                        comments_count = "${it.likes}",
                        altDescription = it.altDescription,
                        blurHash = it.blurHash,
                        color = it.color,
                        createdAt = it.createdAt,
                        description = it.description,
                        height = it.height,
                        likedByUser = it.likedByUser,
                        likes = it.likes,
                        promotedAt = it.promotedAt,
                        updatedAt = it.updatedAt,
                        urls = it.urls,
                        user = it.user,
                        width = it.width
                    )
                )
            }
            return savedPhotoEntityList
        }
    }
}