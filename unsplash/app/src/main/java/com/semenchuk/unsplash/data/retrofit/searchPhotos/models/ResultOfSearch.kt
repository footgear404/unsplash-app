package com.semenchuk.unsplash.data.retrofit.searchPhotos.models

import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import com.semenchuk.unsplash.entities.ResultOfSearchItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultOfSearch(
    @Json(name = "total")
    override val total: Int,
    @Json(name = "total_pages")
    override val totalPages: Int,
    @Json(name = "results")
    override val results: List<UnsplashPhotosItem>
): ResultOfSearchItem