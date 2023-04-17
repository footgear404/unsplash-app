package com.semenchuk.unsplash

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.semenchuk.unsplash.databinding.ActivityMainBinding


const val FIRST_START = "FIRST_START"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = addNavigation()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.onBoardingFragment || destination.id == R.id.splashFragment) {
                binding.navView.visibility = ViewGroup.GONE
            } else {
                binding.navView.visibility = ViewGroup.VISIBLE
            }
        }
    }


    private fun addNavigation(): NavController {
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
        return navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}