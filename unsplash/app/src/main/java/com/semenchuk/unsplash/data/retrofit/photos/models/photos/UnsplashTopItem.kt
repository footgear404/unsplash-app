package com.semenchuk.unsplash.data.retrofit.photos.models.photos

import com.semenchuk.unsplash.entities.PhotoItem

data class UnsplashTopItem(
    override val altDescription: String?,
    override val blurHash: String?,
    override val color: String?,
    override val createdAt: String?,
    override val currentUserCollections: List<Any>?,
    override val description: Any?,
    override val height: Int?,
    override val id: String?,
    override val likedByUser: Boolean?,
    override val likes: Int?,
    override val links: Links?,
    override val promotedAt: String?,
    override val sponsorship: Sponsorship?,
    override val topicSubmissions: TopicSubmissions?,
    override val updatedAt: String?,
    override val urls: Urls?,
    override val user: User?,
    override val width: Int?
) : PhotoItem
