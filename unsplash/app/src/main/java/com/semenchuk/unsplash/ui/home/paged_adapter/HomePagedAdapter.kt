package com.semenchuk.unsplash.ui.home.paged_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.retrofit.like.models.Photo
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.PhotoListItemBinding
import com.semenchuk.unsplash.databinding.TopOfDayItemBinding
import com.semenchuk.unsplash.utils.BaseViewHolder
import com.semenchuk.unsplash.utils.PhotosDiffUtilCallback


class HomePagedAdapter(
    private val photoClickListener: (SavedPhotoEntity) -> Unit,
    private val likeClickListener: (SavedPhotoEntity, Int) -> Unit
) : PagingDataAdapter<SavedPhotoEntity, BaseViewHolder<Any>>(PhotosDiffUtilCallback()) {

    override fun onBindViewHolder(
        holder: BaseViewHolder<Any>,
        position: Int,
    ) {
        val item = getItem(position)

        if (item != null) holder.bind(item)

        setFullWithForFirstHolder(holder, position)
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
                likeCount = (payload[0] as Photo).likes!!.toString()
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

    private fun setFullWithForFirstHolder(holder: BaseViewHolder<Any>, position: Int) {
        if (position == 0) {
            (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                .isFullSpan = true
        }
    }

    companion object {
        const val TOP_OF_DAY: Int = R.layout.top_of_day_item
        const val PHOTO_LIST: Int = R.layout.photo_list_item
    }
}