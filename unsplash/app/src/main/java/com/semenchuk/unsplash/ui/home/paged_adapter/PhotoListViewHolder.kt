package com.semenchuk.unsplash.ui.home.paged_adapter

import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.PhotoListItemBinding
import com.semenchuk.unsplash.utils.GlideImageHelper

class PhotoListViewHolder(
    private val binding: PhotoListItemBinding,
    private val photoClickListener: (SavedPhotoEntity) -> Unit,
    private val likeClickListener: (SavedPhotoEntity, Int) -> Unit
) : BaseViewHolder<Any>(binding) {

    private val glideImageHelper = GlideImageHelper()

    override fun bind(item: SavedPhotoEntity) {

        with(binding) {
            val context = root.context

            userName.text = context.resources.getString(
                R.string.username,
                item.user?.firstName,
                item.user?.lastName ?: ""
            )
            nickname.text = context.resources.getString(R.string.nickname, item.user?.username)
            likesCount.text = item.likes.toString()

            like.isSelected = item.likedByUser

            like.setOnClickListener {
                val updatedItem = updateItem(item, like.isSelected, likesCount.text.toString())
//                item.likedByUser = like.isSelected
//                item.likes = likesCount.text.toString().toInt()
                likeClickListener(updatedItem, layoutPosition)
            }
            backgroundPhoto.setOnClickListener {
                val updatedItem = updateItem(item, like.isSelected, likesCount.text.toString())
//                item.likedByUser = like.isSelected
//                item.likes = likesCount.text.toString().toInt()
                photoClickListener(updatedItem)
            }
//            set photo
            glideImageHelper.setPhoto(
                context = context, img_url = item.urls?.regular,
                into = backgroundPhoto, blurHash = item.blurHash
            )
//            set avatar
            glideImageHelper.setPhoto(
                context = context, img_url = item.user?.profileImage?.medium,
                into = authorProfileImg, blurHash = item.blurHash
            )
        }
    }

//          Update current values in current item
    private fun updateItem(
        item: SavedPhotoEntity,
        isLike: Boolean,
        commentCount: String
    ): SavedPhotoEntity {
        item.likedByUser = isLike
        item.likes = commentCount.toInt()
        return item
    }

    override fun updateLikes(isLike: Boolean, likeCount: String) {
        binding.like.isSelected = isLike
        binding.likesCount.text = likeCount
    }
}