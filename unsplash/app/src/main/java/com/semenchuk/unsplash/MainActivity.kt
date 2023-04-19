package com.semenchuk.unsplash

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.semenchuk.unsplash.databinding.ActivityMainBinding


const val AUTH_STATUS = "AUTH_STATUS"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

//        Disable ActionBar
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        return navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}