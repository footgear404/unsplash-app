package com.semenchuk.unsplash.ui.onBoarding

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Gallery
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.databinding.FragmentOnBoardingBinding
import com.semenchuk.unsplash.ui.home.HomeFragment


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
//        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.skipBtn.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }

        val list = listOf(
            "Создавайте снимки, публикуйте, собирайте аудиторию, получайте фидбек!",
            "Делитесь с друзьями, собирайте коллекции",
            "Загружайте любимые снимки и отслеживайте статистику")

        onboardItemCollectionAdapter = OnboardItemCollectionAdapter(this, list)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = onboardItemCollectionAdapter


        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            val tabText = list[position].split(" ")
//            tab.text = tabText[0]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_OBJECT = "object"
    }
}


