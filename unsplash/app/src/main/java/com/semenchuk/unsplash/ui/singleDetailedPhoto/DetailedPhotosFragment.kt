package com.semenchuk.unsplash.ui.singleDetailedPhoto

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.retrofit.photoById.models.DetailedPhoto
import com.semenchuk.unsplash.databinding.FragmentDetaliedPhotosBinding
import com.semenchuk.unsplash.domain.utils.State
import kotlinx.coroutines.launch

class DetailedPhotosFragment : Fragment() {

    private var _binding: FragmentDetaliedPhotosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailedPhotosViewModel by viewModels { App.appComponent.detailedPhotosViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetaliedPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collect {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        viewModel.sendMessage("Loading")
                        binding.mainContainer.visibility = View.GONE
                    }
                    State.Success -> {
                        setupView(viewModel.photo.value!!)
                        binding.mainContainer.visibility = View.VISIBLE
                    }
                    else -> {
                        viewModel.sendMessage("Error loading")
                    }
                }

            }
        }
    }

    private fun setupView(data: DetailedPhoto) {

        binding.includedImg.todayInTopTextId.text = data.description

        setImg(
            description = "photo",
            img_url = data.urls.regular,
            view = binding.includedImg.backgroundPhoto
        )
        setImg(
            description = "author photo",
            img_url = data.urls.regular,
            view = binding.includedImg.authorProfileImg
        )

        binding.includedImg.userName.text =
            resources.getString(R.string.username, data.user.firstName, data.user.lastName ?: "")
        binding.includedImg.nickname.text =
            resources.getString(R.string.nickname, data.user.firstName)
        binding.includedImg.likesCount.text = data.likes.toString()

        binding.location.text = resources.getString(R.string.location, data.location.name, "")

        binding.cameraName.text = resources.getString(R.string.madeWith, data.exif.name)
        binding.model.text = resources.getString(R.string.model, data.exif.model)
        binding.exposure.text = resources.getString(R.string.exposure, data.exif.exposure_time)
        binding.aperture.text = resources.getString(R.string.aperture, data.exif.aperture)
        binding.focalLength.text = resources.getString(R.string.focalLength, data.exif.focal_length)
        binding.iso.text = resources.getString(R.string.iso, data.exif.iso)
        binding.description.text = resources.getString(R.string.description, data.description)
        binding.aboutAuthor.text =
            resources.getString(R.string.aboutAuthor, data.user.username, data.user.bio)
    }

    private fun setImg(description: String, img_url: String, view: ImageView) {
        Log.d("DF", "setImg to: $description")
        Glide.with(this@DetailedPhotosFragment)
            .load(img_url)
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
                    binding.includedImg.photoProgress.isVisible = false
                    return false
                }

            })
            .error(R.drawable.baseline_error_24)
            .transition(DrawableTransitionOptions.withCrossFade())
            .dontAnimate()
            .into(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}