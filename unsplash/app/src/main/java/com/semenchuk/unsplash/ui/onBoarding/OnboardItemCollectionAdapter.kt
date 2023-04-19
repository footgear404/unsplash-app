package com.semenchuk.unsplash.ui.onBoarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class OnboardItemCollectionAdapter(
    fragment: Fragment,
    private val list: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
//        fragment.arguments = Bundle().apply {
//            putString(OnBoardingFragment.ARG_OBJECT, list[position])
//        }
        return OnBoardItemFragment(onBoardText = list[position])
    }
}


