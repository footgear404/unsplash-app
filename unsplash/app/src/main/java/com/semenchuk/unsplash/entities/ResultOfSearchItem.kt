package com.semenchuk.unsplash.entities

interface ResultOfSearchItem {
    val total: Int
    val totalPages: Int
    val results: List<PhotoItem>
}