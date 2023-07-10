package com.semenchuk.unsplash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity
import com.semenchuk.unsplash.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = addNavigation()

        val hideNavBarList: List<Int> = listOf(
            R.id.onBoardingFragment,
            R.id.splashFragment,
            R.id.authorizationFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in hideNavBarList) {
                binding.navView.visibility = ViewGroup.GONE
                (getActivity(this) as AppCompatActivity?)!!.supportActionBar!!.hide()
            } else {
                (getActivity(this) as AppCompatActivity?)!!.supportActionBar!!.show()
                binding.navView.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun addNavigation(): NavController {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

//        Disable ActionBar


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.collectionsFragment, R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        return navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}