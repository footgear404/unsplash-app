package com.semenchuk.unsplash.ui.detailedPhoto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
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
import com.semenchuk.unsplash.data.retrofit.photoById.models.Tag
import com.semenchuk.unsplash.databinding.FragmentDetaliedPhotosBinding
import com.semenchuk.unsplash.domain.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailedPhotosFragment : Fragment() {

    private var _binding: FragmentDetaliedPhotosBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailedPhotosFragmentArgs>()
    private val viewModel: DetailedPhotosViewModel by viewModels { App.appComponent.detailedPhotosViewModelFactory() }
    private var isAllGranted = false
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                Toast.makeText(requireContext(), "Thank you for trusting us", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "You declined permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

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

        setFullSizeImageView()
        setImage()

        binding.includedImg.like.setOnClickListener {
            viewModel.setLike(args.photoItem.id, binding.includedImg.like.isSelected)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.likeUnlike.collect { photo ->
                if (photo != null) {
                    binding.includedImg.likesCount.text = photo.likes.toString()
                    binding.includedImg.like.isSelected = photo.likedByUser
                } else {
                    viewModel.sendMessage(getString(R.string.error))
                }
            }
        }


        binding.download.setOnClickListener {
            if (checkPermissions()) {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    val saveResult = viewModel.saveImage(
                        loadImageFromUrl(url = args.photoItem.urls!!.full),
                        imageName = args.photoItem.id
                    )
                    if (saveResult != null) {
                        viewModel.sendMessage(getString(R.string.download_success, saveResult))
                    }
                }
            } else {
                launcher.launch(REQUEST_PERMISSIONS)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        binding.detailsContainer.visibility = View.GONE
                        binding.detailsProgress.visibility = View.VISIBLE
                    }
                    State.Success -> {
                        setupDetails(viewModel.photo.value!!)
                        binding.detailsContainer.visibility = View.VISIBLE
                        binding.detailsProgress.visibility = View.GONE
                    }
                    is State.Error -> {
                        binding.detailsContainer.visibility = View.GONE
                        binding.detailsProgress.visibility = View.VISIBLE
                        viewModel.sendMessage(state.message)
                    }
                    else -> {
                        viewModel.sendMessage("Else")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collect {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setImage() {
        setImageToView(
            description = "photo",
            img_url = args.photoItem.urls!!.regular,
            view = binding.includedImg.backgroundPhoto
        )
        setImageToView(
            description = "author photo",
            img_url = args.photoItem.urls!!.regular,
            view = binding.includedImg.authorProfileImg
        )
        binding.includedImg.like.isSelected = args.photoItem.likedByUser == true
        binding.includedImg.userName.text =
            resources.getString(
                R.string.username,
                args.photoItem.user!!.firstName,
                args.photoItem.user!!.lastName ?: ""
            )
        binding.includedImg.nickname.text =
            resources.getString(R.string.nickname, args.photoItem.user!!.firstName)
        binding.includedImg.likesCount.text = args.photoItem.likes.toString()
    }

    private fun setFullSizeImageView() {
        viewModel.load(id = args.photoItem.id)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        binding.includedImg.backgroundPhoto.layoutParams = layoutParams
    }

    private fun loadImageFromUrl(url: String?): Bitmap? {
        return try {
            viewModel.sendMessage(getString(R.string.download_start))
            Glide.with(requireContext())
                .asBitmap()
                .load(url)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .submit()
                .get()
        } catch (e: Exception) {
            Log.d("DOWNLOAD", "loadImageFromUrl failed: $e")
            viewModel.sendMessage(getString(R.string.download_failed))
            return null
        }
    }

    private fun setupDetails(data: DetailedPhoto) {
        binding.includedImg.header.text = resources.getString(
            R.string.description, data.description
                ?: resources.getString(R.string.no_description)
        )
        binding.location.text = resources.getString(
            R.string.location,
            data.location.name ?: resources.getString(R.string.unknown)
        )
        binding.tags.text = collectTags(data.tags)
        binding.cameraName.text = resources.getString(
            R.string.madeWith,
            data.exif.name ?: resources.getString(R.string.unknown)
        )
        binding.model.text = resources.getString(
            R.string.model,
            data.exif.model ?: resources.getString(R.string.unknown)
        )
        binding.exposure.text = resources.getString(
            R.string.exposure,
            data.exif.exposure_time ?: resources.getString(R.string.unknown)
        )
        binding.aperture.text = resources.getString(
            R.string.aperture,
            data.exif.aperture ?: resources.getString(R.string.unknown)
        )
        binding.focalLength.text = resources.getString(
            R.string.focalLength,
            data.exif.focal_length ?: resources.getString(R.string.unknown)
        )
        binding.iso.text = resources.getString(
            R.string.iso,
            data.exif.iso ?: resources.getString(R.string.unknown)
        )
        binding.aboutAuthor.text =
            resources.getString(
                R.string.aboutAuthor,
                data.user.username,
                data.user.bio ?: resources.getString(R.string.no_about)
            )
        binding.location.setOnClickListener {
            val latitude = data.location.position?.latitude ?: 0.0
            val longitude = data.location.position?.longitude ?: 0.0

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:$latitude,$longitude")
            )
            try {
                startActivity(Intent.createChooser(intent, getString(R.string.open_with_text)))
            } catch (e: Exception) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sendMessage(getString(R.string.map_app_error))
                }
            }
        }
    }

    private fun collectTags(tags: List<Tag>): String {
        var result = ""
        tags.forEach {
            result += "#${it.title} "
        }
        return result
    }

    private fun setImageToView(description: String, img_url: String, view: ImageView) {
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

    private fun checkPermissions(): Boolean {
        isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(this.requireContext(), permission) ==
                    PackageManager.PERMISSION_GRANTED
        }
        return if (isAllGranted) {
            Log.d("PERMISSIONS", "All permissions is granted"); true
        } else {
            Log.d("PERMISSIONS", "Permissions not granted"); false
        }
    }

    companion object {
        private val REQUEST_PERMISSIONS = buildList {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}