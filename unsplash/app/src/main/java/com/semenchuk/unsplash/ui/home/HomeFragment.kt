package com.semenchuk.unsplash.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.semenchuk.unsplash.App
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.databinding.FragmentHomeBinding
import com.semenchuk.unsplash.ui.home.paged_adapter.UnsplashPagedAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val pagedAdapter = UnsplashPagedAdapter()


    private val viewModel: HomeViewModel by viewModels { App.appComponent.homeViewModelFactory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = pagedAdapter

        setupMenu()

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.reloadPhotos()
            binding.swipeToRefresh.isRefreshing = false
        }

        viewModel.photos.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {}

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_search_menu, menu)
                val searchItem: MenuItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                setSearchViewConfigs(searchView)

                enableOnBackPressedCallBack(set = true, searchView = searchView)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Log.d("TAG", "onMenuItemSelected: ")
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun enableOnBackPressedCallBack(set: Boolean, searchView: SearchView) {
        when (set) {
            true -> {
                val mainActivity = requireActivity()
                mainActivity.onBackPressedDispatcher.addCallback(
                    viewLifecycleOwner,
                    object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            when (!searchView.isIconified) {
                                true -> searchView.onActionViewCollapsed()
                                else -> {
                                    isEnabled = false
                                    mainActivity.onBackPressedDispatcher.onBackPressed()
                                }
                            }
                        }
                    }
                )
            }
            false -> {
                Log.d("TAG", "enableOnBackPressedCallBack: $set")
            }
        }
    }

    private fun setSearchViewConfigs(searchView: SearchView) {
        val searchPlate =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = getString(R.string.search)
        val searchPlateView: View =
            searchView.findViewById(androidx.appcompat.R.id.search_plate)
        val searchButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeButton =
            searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        closeButton.setColorFilter(R.color.black_500)

        searchPlate.setHintTextColor(resources.getColor(R.color.black_500, context?.theme))
        searchButton.setColorFilter(resources.getColor(R.color.black_500, context?.theme))
        closeButton.setColorFilter(resources.getColor(R.color.black_500, context?.theme))


        searchView.setOnCloseListener {
            searchView.onActionViewCollapsed()
            return@setOnCloseListener true
        }

        searchPlateView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
