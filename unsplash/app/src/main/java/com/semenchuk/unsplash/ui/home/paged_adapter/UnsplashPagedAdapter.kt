package com.semenchuk.unsplash.ui.home.paged_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem
import com.semenchuk.unsplash.databinding.PhotoListItemBinding

class UnsplashPagedAdapter :
    PagingDataAdapter<UnsplashPhotosItem, ViewHolder>(PhotosDiffUtilCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.binding.root.context
        Glide.with(context).load(item?.urls?.thumb).into(holder.binding.photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhotoListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class ViewHolder(
    val binding: PhotoListItemBinding
) : RecyclerView.ViewHolder(binding.root)


class PhotosDiffUtilCallback() : DiffUtil.ItemCallback<UnsplashPhotosItem>() {
    override fun areItemsTheSame(
        oldItem: UnsplashPhotosItem,
        newItem: UnsplashPhotosItem
    ): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: UnsplashPhotosItem,
        newItem: UnsplashPhotosItem
    ): Boolean = oldItem == newItem
}