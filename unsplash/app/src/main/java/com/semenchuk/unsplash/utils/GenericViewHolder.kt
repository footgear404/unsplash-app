package com.semenchuk.unsplash.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(position: Int)

}