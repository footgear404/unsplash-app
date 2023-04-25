package com.semenchuk.unsplash.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        viewLifecycleOwner.lifecycleScope.launch {
            val retrofit  = RetrofitService().getPhotos.send(
                authHeader = "Bearer lJHnRy25xm5uNzJzxneToLxaHHieIpL49vVHeUq1tdI",
                page = 1,
                per_page = 10
            )
            Log.d("TAG", "retrofit: ${retrofit.body()} ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}