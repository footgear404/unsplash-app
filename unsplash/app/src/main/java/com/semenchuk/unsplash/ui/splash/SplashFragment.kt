package com.semenchuk.unsplash.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.appAuth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var isAuthorized: Boolean? = null
    private val sp = App.appComponent.sharedPrefs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isAuthorized = sp.getString(AuthRepository.ACCESS_TOKEN, null) != null

        Log.d("TAG", "isAuthorized launch: $isAuthorized")

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1500)
            if (isAuthorized == true) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }
        }
    }
}