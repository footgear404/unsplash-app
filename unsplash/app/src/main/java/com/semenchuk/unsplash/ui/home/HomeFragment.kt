package com.semenchuk.unsplash.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.semenchuk.unsplash.App
import com.semenchuk.unsplash.databinding.FragmentHomeBinding
import com.semenchuk.unsplash.ui.home.paged_adapter.UnsplashPagedAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val pagedAdapter = UnsplashPagedAdapter()

    private val viewModel: HomeViewModel by viewModels { App.appComponent.homeViewModelFactory() }

//    private val pagedAdapter =

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = pagedAdapter


//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.photos.collectLatest {
//                pagedAdapter.submitData(it)
//            }
//        }

        viewModel.photos.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}