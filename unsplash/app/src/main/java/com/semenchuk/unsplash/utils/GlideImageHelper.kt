package com.semenchuk.unsplash.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.semenchuk.unsplash.R

class GlideImageHelper {
    fun setPhoto(
        context: Context,
        img_url: String?,
        blurHash: String,
        into: ImageView,
        description: String = "",
        isAvatar: Boolean = false
    ) {

        val blurHashPlaceholder: Drawable =
            BitmapDrawable(context.resources, BlurHashDecoder.decode(blurHash, 20, 12))

        Glide.with(context)
            .load(img_url)
            .placeholder(blurHashPlaceholder)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
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
                    return false
                }

            })
            .error(R.drawable.baseline_error_24)
            .transition(TRANSITION)
            .dontAnimate()
            .into(into)
    }

    companion object {
        val TRANSITION = DrawableTransitionOptions.withCrossFade()
        const val PLACEHOLDER = R.drawable.sample_img
    }
}