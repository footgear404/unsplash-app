package com.semenchuk.unsplash

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.semenchuk.unsplash.databinding.ActivityMainBinding
import com.semenchuk.unsplash.ui.onBoarding.OnBoardingFragment


const val FIRST_START = "FIRST_START"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val getPrefs = applicationContext.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val isFirstStart = getPrefs.getBoolean(FIRST_START, true)

        if (isFirstStart) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, OnBoardingFragment())
                .commit()
//            val e = getPrefs.edit()
//            e.putBoolean(FIRST_START, false)
//            e.apply()
            binding.navView.visibility = ViewGroup.GONE
        } else {
            addNavigation()
        }

    }


    private fun addNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment
            )
        )
//        Disable ActionBar
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}