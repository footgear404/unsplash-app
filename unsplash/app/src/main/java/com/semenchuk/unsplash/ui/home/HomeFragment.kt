package com.semenchuk.unsplash.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.data.room.photos.SavedPhotoEntity
import com.semenchuk.unsplash.databinding.FragmentHomeBinding
import com.semenchuk.unsplash.domain.utils.State
import com.semenchuk.unsplash.ui.home.paged_adapter.UnsplashPagedAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val pagedAdapter =
        UnsplashPagedAdapter(
            photoClickListener = { item -> onPhotoClick(item) },
            likeClickListener = { item, position ->
                onLikeClick(item, position)
            }
        )

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

        setMenu()
        setAdapter()

        binding.swipeToRefresh.setOnRefreshListener {
            if (checkForInternet(requireContext())) {
                viewModel.load()
                pagedAdapter.refresh()
                binding.swipeToRefresh.isRefreshing = false
            } else {
                viewModel.sendMessageInSnack(this@HomeFragment.getString(R.string.no_internet))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.likeUnlike.collect { payload ->
                if (payload.second != null) {
                    pagedAdapter.notifyItemChanged(payload.first, payload.second)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.message.collect { message ->
                Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                Log.d("TAG", "state: $state")
                when (state) {
                    State.Loading -> {
                        binding.swipeToRefresh.isRefreshing = true
                        viewModel.sendMessageInSnack(this@HomeFragment.getString(R.string.searching))
                    }
                    State.Success -> {
                        viewModel.photos.onEach {
                            pagedAdapter.submitData(it)
                        }.launchIn(viewLifecycleOwner.lifecycleScope)
                        pagedAdapter.refresh()
                        binding.swipeToRefresh.isRefreshing = false
                    }
                    is State.Error -> {
                        Snackbar.make(binding.recyclerView, state.message, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun onPhotoClick(it: SavedPhotoEntity) {
        Log.d("CLICK", "PHOTO: $it")
        val direction = HomeFragmentDirections.actionHomeFragmentToDetailedPhotosFragment(it)
        findNavController().navigate(direction)
    }

    private fun onLikeClick(it: SavedPhotoEntity, position: Int) {
        Log.d("CLICK", "LIKE: $it")
        viewModel.setLike(it.id, it.likedByUser, position)
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = pagedAdapter
    }

    private fun setMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {}

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_search_menu, menu)
                val searchItem: MenuItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                setSearchViewConfigs(searchView)

                enableOnBackPressedCallBack(searchView = searchView)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Log.d("TAG", "onMenuItemSelected: ")
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun enableOnBackPressedCallBack(set: Boolean = true, searchView: SearchView) {
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
            else -> {
                Log.d("TAG", "enableOnBackPressedCallBack disabled")
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
                    if (checkForInternet(requireContext())) {
                        viewModel.load(query.toString())
                        pagedAdapter.refresh()
                        viewModel.sendMessageInSnack(
                            this@HomeFragment.getString(
                                R.string.searching,
                                query.toString()
                            )
                        )
                    } else {
                        viewModel.sendMessageInSnack(this@HomeFragment.getString(R.string.no_internet))
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
