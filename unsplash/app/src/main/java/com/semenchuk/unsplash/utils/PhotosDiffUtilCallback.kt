package com.semenchuk.unsplash.utils

import androidx.recyclerview.widget.DiffUtil
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity

class PhotosDiffUtilCallback : DiffUtil.ItemCallback<SavedPhotoEntity>() {
    override fun areItemsTheSame(
        oldItem: SavedPhotoEntity,
        newItem: SavedPhotoEntity
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: SavedPhotoEntity,
        newItem: SavedPhotoEntity
    ): Boolean = oldItem == newItem
}