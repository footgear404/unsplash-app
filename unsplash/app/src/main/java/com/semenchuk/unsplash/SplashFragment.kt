package com.semenchuk.unsplash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var isFirstStart: Boolean? = null
    private var _getPrefs: SharedPreferences? = null
    private val getPrefs get() = _getPrefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _getPrefs = requireActivity().applicationContext.getSharedPreferences(
            "appPrefs",
            Context.MODE_PRIVATE
        )
        isFirstStart = getPrefs.getBoolean(FIRST_START, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500)
            val e = getPrefs.edit()
            if (isFirstStart!!) {
                e.putBoolean(FIRST_START, false)
                e.apply()
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            } else {
                e.putBoolean(FIRST_START, true)
                e.apply()
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }
}