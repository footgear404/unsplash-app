package com.semenchuk.unsplash.ui.onBoarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {
    private lateinit var onboardItemCollectionAdapter: OnboardItemCollectionAdapter
    private lateinit var viewPager: ViewPager2

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.skipBtn.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_authorizationFragment)
        }

        onboardItemCollectionAdapter = OnboardItemCollectionAdapter(
            this, resources.getStringArray(
                LIST_STRING
            ).toList()
        )
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = onboardItemCollectionAdapter


        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.btnBack.isVisible = position != 0
                    binding.btnNext.isVisible = position != 2
                    binding.skipBtn.isVisible = position == 2
                }
            })

        binding.btnBack.setOnClickListener {
            viewPager.currentItem--
        }
        binding.btnNext.setOnClickListener {
            val scale = loadAnimation(requireContext(), R.anim.pagination_anim)
            binding.btnNext.startAnimation(scale)
            viewPager.currentItem++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val LIST_STRING = R.array.onBoardText
    }
}


