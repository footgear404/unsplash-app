package com.semenchuk.unsplash.ui.home.paged_adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.databinding.PhotoListItemBinding
import com.semenchuk.unsplash.databinding.TopOfDayItemBinding

import com.semenchuk.unsplash.entities.PhotoItem


class UnsplashPagedAdapter(private val onClick: (PhotoItem) -> Unit) :
    PagingDataAdapter<PhotoItem, BaseViewHolder<Any>>(PhotosDiffUtilCallback()) {
    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.rootView.setOnClickListener {
                onClick(item)
            }
        }


        if (position == 0) {
            val fullWidthItem =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            fullWidthItem.isFullSpan = true

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TOP_OF_DAY
        } else {
            PHOTO_LIST
        }

//        val itemType = getItem(position)
//        return when (getItem(position)) {
//            is UnsplashPhotosItem -> PHOTO_LIST
//            is UnsplashTopItem -> TOP_OF_DAY
//
//            else -> {
//                throw java.lang.IllegalStateException("ELSE in getItemViewType()")
//            }
//        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Any> {
        return when (viewType) {
            TOP_OF_DAY -> TopOfDayViewHolder(
                TopOfDayItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            PHOTO_LIST -> PhotoListViewHolder(
                PhotoListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw java.lang.IllegalStateException("ELSE in onCreateViewHolder()")
            }
        }
    }

    companion object {
        const val TOP_OF_DAY: Int = R.layout.top_of_day_item
        const val PHOTO_LIST: Int = R.layout.photo_list_item
    }
}

class PhotoListViewHolder(
    private val binding: PhotoListItemBinding
) : BaseViewHolder<Any>(binding) {

    override fun bind(item: Any) {
        val context = binding.root.context
        item as PhotoItem
        binding.userName.text = context.resources.getString(R.string.username, item.user?.firstName, item.user?.lastName ?: "")
        binding.nickname.text = context.resources.getString(R.string.nickname, item.user?.username)
        binding.likesCount.text = item.likes.toString()

        Glide.with(context)
            .load(item.urls?.regular)
            .placeholder(R.drawable.sample_img)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoProgress.isVisible = false
                    return false
                }

            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.baseline_error_24)
            .dontAnimate()
            .into(binding.backgroundPhoto)

        Glide.with(context).load(item.user?.profileImage?.medium)
            .into(binding.authorProfileImg)
    }
}

class TopOfDayViewHolder(
    private val binding: TopOfDayItemBinding
) : BaseViewHolder<Any>(binding) {
    override fun bind(item: Any) {
        val context = binding.root.context
        item as PhotoItem
        binding.userName.text = context.resources.getString(R.string.username, item.user?.firstName, item.user?.lastName ?: "")
        binding.nickname.text = context.resources.getString(R.string.nickname, item.user?.username)
        binding.likesCount.text = item.likes.toString()
        Glide.with(context)
            .load(item.urls?.regular)
            .placeholder(R.drawable.sample_img)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.photoProgress.isVisible = false
                    return false
                }

            })
            .error(R.drawable.baseline_error_24)
            .transition(DrawableTransitionOptions.withCrossFade())
            .dontAnimate()
            .into(binding.backgroundPhoto)

        Glide.with(context).load(item.user?.profileImage?.medium)
            .into(binding.authorProfileImg)
    }
}


class PhotosDiffUtilCallback : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(
        oldItem: PhotoItem,
        newItem: PhotoItem
    ): Boolean =
        oldItem.id == newItem.id


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: PhotoItem,
        newItem: PhotoItem
    ): Boolean = oldItem == newItem
}