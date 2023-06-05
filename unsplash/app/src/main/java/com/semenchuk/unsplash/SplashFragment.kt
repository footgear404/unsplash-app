package com.semenchuk.unsplash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var isAuthorized: Boolean? = null
//    private var _getPrefs: SharedPreferences? = null
//    private val getPrefs get() = _getPrefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        _getPrefs = requireActivity().applicationContext.getSharedPreferences(
//            "appPrefs",
//            Context.MODE_PRIVATE
//        )
//        isAuthorized = getPrefs.getString(AUTH_STATUS, null) != null

        val sp = App.appComponent.sharedPrefs()
        isAuthorized = sp.getString(AUTH_STATUS, null) != null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        Log.d("TAG", "isAuthorized launch: $isAuthorized")

        println(isAuthorized)

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