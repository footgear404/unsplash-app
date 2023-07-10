package com.semenchuk.unsplash.domain.utils

import com.semenchuk.unsplash.data.retrofit.profile.models.ProfileDto
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.data.room.profile.SavedProfile
import com.semenchuk.unsplash.entities.PhotoEntity

class Mapper {
    companion object {
        fun toSavedPhotoEntity(unsplashPhotosItem: List<PhotoEntity>): List<SavedPhotoEntity> {
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

        fun profileDtoToSavedProfile(profile: ProfileDto): SavedProfile {
            return SavedProfile(
                id = profile.id,
                bio = profile.bio,
                downloads = profile.downloads,
                email = profile.email,
                firstName = profile.firstName,
                followedByUser = profile.followedByUser,
                instagramUsername = profile.instagramUsername,
                lastName = profile.lastName,
                links = profile.links,
                location = profile.location,
                portfolioUrl = profile.portfolioUrl,
                totalCollections = profile.totalCollections,
                totalLikes = profile.totalLikes,
                totalPhotos = profile.totalPhotos,
                twitterUsername = profile.twitterUsername,
                updatedAt = profile.updatedAt,
                uploadsRemaining = profile.uploadsRemaining,
                username = profile.username,
                profileImage = profile.profileImage
            )
        }

        fun savedProfileToProfileDto(profile: SavedProfile): ProfileDto {
            return ProfileDto(
                id = profile.id,
                bio = profile.bio,
                downloads = profile.downloads,
                email = profile.email,
                firstName = profile.firstName,
                followedByUser = profile.followedByUser,
                instagramUsername = profile.instagramUsername,
                lastName = profile.lastName,
                links = profile.links,
                location = profile.location,
                portfolioUrl = profile.portfolioUrl,
                totalCollections = profile.totalCollections,
                totalLikes = profile.totalLikes,
                totalPhotos = profile.totalPhotos,
                twitterUsername = profile.twitterUsername,
                updatedAt = profile.updatedAt,
                uploadsRemaining = profile.uploadsRemaining,
                username = profile.username,
                profileImage = profile.profileImage
            )
        }
    }
}