package com.semenchuk.unsplash.ui.singleDetailedPhoto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.semenchuk.unsplash.app.App
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

        viewModel.load("V8w0gSmxajY")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {

                    }
                    State.Success -> {
                        val data = viewModel.photo?.value
                        Log.d("TAG", "onViewCreated: $data")
                        Glide.with(this@DetailedPhotosFragment)
                            .load(data?.urls?.regular)
                            .into(binding.includedImg.backgroundPhoto)
                    }
                    else -> {

                    }
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}