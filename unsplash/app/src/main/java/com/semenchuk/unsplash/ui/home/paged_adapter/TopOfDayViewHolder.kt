package com.semenchuk.unsplash.ui.home.paged_adapter

import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.TopOfDayItemBinding
import com.semenchuk.unsplash.utils.BaseViewHolder
import com.semenchuk.unsplash.utils.GlideImageHelper

class TopOfDayViewHolder(
    private val binding: TopOfDayItemBinding,
    private val photoClickListener: (SavedPhotoEntity) -> Unit,
    private val likeClickListener: (SavedPhotoEntity, Int) -> Unit
) : BaseViewHolder<Any>(binding) {

    private val glideImageHelper = GlideImageHelper()

    override fun bind(item: SavedPhotoEntity) {
        val context = binding.root.context

        with(binding) {
            header.text = context.resources.getString(R.string.today_in_the_top)
            userName.text = context.resources.getString(
                R.string.username,
                item.user?.firstName,
                item.user?.lastName ?: ""
            )
            nickname.text =
                context.resources.getString(R.string.nickname, item.user?.username)
            likesCount.text = item.likes.toString()

            like.isSelected = item.likedByUser == true

            like.setOnClickListener {
//                Update for current values in item
                item.likedByUser = like.isSelected
                item.likes = likesCount.text.toString().toInt()

                likeClickListener(item, layoutPosition)
            }
            backgroundPhoto.setOnClickListener {
                photoClickListener(item)
            }

            glideImageHelper.setPhoto(
                context = context, img_url = item.urls?.regular,
                into = backgroundPhoto, blurHash = item.blurHash
            )
            glideImageHelper.setPhoto(
                context = context, img_url = item.user?.profileImage?.medium,
                into = authorProfileImg, blurHash = item.blurHash
            )
        }
    }

    override fun updateLikes(isLike: Boolean, likeCount: String) {
        binding.like.isSelected = isLike
        binding.likesCount.text = likeCount
    }
}