package com.semenchuk.unsplash.entities

interface ResultOfSearchEntity {
    val total: Int
    val totalPages: Int
    val results: List<PhotoEntity>
}