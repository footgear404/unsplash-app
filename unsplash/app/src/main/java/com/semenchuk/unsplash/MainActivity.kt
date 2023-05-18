package com.semenchuk.unsplash

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity
import com.semenchuk.unsplash.databinding.ActivityMainBinding


const val AUTH_STATUS = "AUTH_STATUS"

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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.app_bar_menu, menu)
//        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
//        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
//        searchView.setOnCloseListener {
//            searchView.onActionViewCollapsed()
//            return@setOnCloseListener true
//        }
//
//        val searchPlate =
//            searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
//        searchPlate.hint = getString(R.string.search)
//        val searchPlateView: View =
//            searchView.findViewById(androidx.appcompat.R.id.search_plate)
//        searchPlateView.setBackgroundColor(
//            ContextCompat.getColor(
//                this,
//                android.R.color.transparent
//            )
//        )
//
//        this.onBackPressedDispatcher.addCallback(
//            this,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    when (searchView.isIconified) {
//                        true -> searchView.onActionViewCollapsed()
//                        else -> {
//                            isEnabled = false
//                            this@MainActivity.onBackPressedDispatcher.onBackPressed()
//                        }
//                    }
//                }
//            }
//        )
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//
//        val searchManager =
//            getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}