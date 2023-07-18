package com.semenchuk.unsplash.ui.profile.savedPhotoAdapetr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.PhotoListItemBinding
import com.semenchuk.unsplash.databinding.ProfileViewBinding
import com.semenchuk.unsplash.utils.BaseViewHolder
import com.semenchuk.unsplash.utils.GenericViewHolder
import com.semenchuk.unsplash.utils.PhotosDiffUtilCallback

class SavedPhotoPagedAdapter(
    private val photoClickListener: (SavedPhotoEntity) -> Unit,
    private val likeClickListener: (SavedPhotoEntity, Int) -> Unit
) : PagingDataAdapter<SavedPhotoEntity, GenericViewHolder<View>>(PhotosDiffUtilCallback()) {

    override fun getItemCount(): Int =  super.getItemCount() + 1

    override fun onBindViewHolder(holder: GenericViewHolder<View>, position: Int) {
        holder.bindView(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<View> {
        return when (viewType) {
            PROFILE_VIEW -> ProfileViewHolder(
                ProfileViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            PHOTO_LIST_VIEW -> PhotoListItemViewHolder(
                PhotoListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw java.lang.IllegalStateException("Illegal view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> PROFILE_VIEW
            else -> PHOTO_LIST_VIEW
        }
    }

    private fun setFullWithForFirstHolder(holder: BaseViewHolder<Any>, position: Int) {
        if (position == 0) {
            (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                .isFullSpan = true
        }
    }

    companion object {
        const val PROFILE_VIEW: Int = 1
        const val PHOTO_LIST_VIEW: Int = 2
    }
}