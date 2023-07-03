package com.semenchuk.unsplash.ui.home.paged_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.retrofit.like.models.Photo
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.PhotoListItemBinding
import com.semenchuk.unsplash.databinding.TopOfDayItemBinding
import com.semenchuk.unsplash.ui.utils.GlideImageHelper


class UnsplashPagedAdapter(
    private val photoClickListener: (SavedPhotoEntity) -> Unit,
    private val likeClickListener: (SavedPhotoEntity, Int) -> Unit
) : PagingDataAdapter<SavedPhotoEntity, BaseViewHolder<Any>>(PhotosDiffUtilCallback()) {


    override fun onBindViewHolder(
        holder: BaseViewHolder<Any>,
        position: Int,
    ) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }

        if (position == 0) {
            val fullWidthItem =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            fullWidthItem.isFullSpan = true
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<Any>,
        position: Int,
        payload: MutableList<Any>
    ) {
        if (payload.isEmpty()) {
            super.onBindViewHolder(holder, position, payload)
        } else {
            holder.updateLikes(
                isLike = (payload[0] as Photo).likedByUser,
                likeCount = (payload[0] as Photo).likes!!
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TOP_OF_DAY
        } else {
            PHOTO_LIST
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Any> {
        return when (viewType) {
            TOP_OF_DAY -> TopOfDayViewHolder(
                TopOfDayItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), photoClickListener, likeClickListener
            )
            PHOTO_LIST -> PhotoListViewHolder(
                PhotoListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), photoClickListener, likeClickListener
            )
            else -> {
                throw java.lang.IllegalStateException("Illegal view type")
            }
        }
    }

    companion object {
        const val TOP_OF_DAY: Int = R.layout.top_of_day_item
        const val PHOTO_LIST: Int = R.layout.photo_list_item
    }
}

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
                likeClickListener(item, layoutPosition)
            }
            backgroundPhoto.setOnClickListener {
                photoClickListener(item)
            }

            glideImageHelper.setPhoto(
                context = context, img_url = item.urls?.regular,
                into = backgroundPhoto
            )
            glideImageHelper.setPhoto(
                context = context, img_url = item.user?.profileImage?.medium,
                into = authorProfileImg
            )
        }
    }

    override fun updateLikes(isLike: Boolean, likeCount: Int) {
        binding.like.isSelected = isLike
        binding.likesCount.text = likeCount.toString()
    }
}

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

            like.isSelected = item.likedByUser == true

            like.setOnClickListener {
                likeClickListener(item, layoutPosition)
            }
            backgroundPhoto.setOnClickListener {
                photoClickListener(item)
            }
//            set photo
            glideImageHelper.setPhoto(
                context = context, img_url = item.urls?.regular,
                into = backgroundPhoto
            )
//            set avatar
            glideImageHelper.setPhoto(
                context = context, img_url = item.user?.profileImage?.medium,
                into = authorProfileImg
            )
        }
    }

    override fun updateLikes(isLike: Boolean, likeCount: Int) {
        binding.like.isSelected = isLike
        binding.likesCount.text = likeCount.toString()
    }
}

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