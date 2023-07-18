package com.semenchuk.unsplash.ui.profile.savedPhotoAdapetr

import android.view.View
import com.semenchuk.unsplash.databinding.ProfileViewBinding
import com.semenchuk.unsplash.utils.GenericViewHolder

class ProfileViewHolder(private val binding: ProfileViewBinding): GenericViewHolder<View>(binding.root) {

    override fun bindView(position: Int) {
        binding.userName
    }

}