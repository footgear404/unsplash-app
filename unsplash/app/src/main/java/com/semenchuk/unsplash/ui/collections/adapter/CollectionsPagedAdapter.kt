package com.semenchuk.unsplash.ui.collections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.retrofit.collections.models.CollectionsItem
import com.semenchuk.unsplash.databinding.CollectionItemBinding
import com.semenchuk.unsplash.utils.GlideImageHelper

class CollectionsPagedAdapter :
    PagingDataAdapter<CollectionsItem, CollectionsViewHolder>(CollectionsDiffUtilCallback()) {
    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val item = getItem(position)!!
        val context = holder.binding.root.context
        holder.binding.collectionTitle.text = item.title
        holder.binding.countPhotoHolder.text =
            context.getString(R.string.photos_count, item.totalPhotos.toString())

        GlideImageHelper().setPhoto(
            context,
            item.coverPhoto?.urls?.regular,
            item.coverPhoto?.blurHash!!,
            holder.binding.backgroundPhoto
        )
        GlideImageHelper().setPhoto(
            context,
            item.coverPhoto.user?.profileImage?.medium,
            item.coverPhoto.blurHash,
            holder.binding.authorProfileImg
        )
        holder.binding.userName.text = context.resources.getString(
            R.string.username,
            item.user?.firstName,
            item.user?.lastName ?: ""
        )
        holder.binding.nickname.text = context.resources.getString(R.string.nickname, item.user?.username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        return CollectionsViewHolder(
            CollectionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class CollectionsViewHolder(
    val binding: CollectionItemBinding
) : RecyclerView.ViewHolder(binding.root)

class CollectionsDiffUtilCallback() : DiffUtil.ItemCallback<CollectionsItem>() {
    override fun areItemsTheSame(oldItem: CollectionsItem, newItem: CollectionsItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CollectionsItem, newItem: CollectionsItem): Boolean =
        oldItem == newItem
}