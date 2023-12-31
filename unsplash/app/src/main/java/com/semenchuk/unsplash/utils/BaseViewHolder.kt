package com.semenchuk.unsplash.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity


abstract class BaseViewHolder<T : Any>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: SavedPhotoEntity)
    abstract fun updateLikes(isLike: Boolean, likeCount: String)
}

