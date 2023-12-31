package com.semenchuk.unsplash.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.databinding.FragmentProfileBinding
import com.semenchuk.unsplash.utils.GlideImageHelper
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

//    private val pagedAdapter = SavedPhotoPagedAdapter()

    private val viewModel: ProfileViewModel by viewModels { App.appComponent.profileViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLogout.collect {
                when (it) {
                    true -> findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
                    false -> Log.d("LOGOUT", "logout $it")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profile.collect {
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = it?.username
                binding.profile.userName.text = it?.firstName + " " + it?.lastName
                binding.profile.nickname.text = "@" + it?.username
                binding.profile.location.text = it?.location
                binding.profile.email.text = it?.email
                binding.profile.authorDescription.text = it?.bio

                GlideImageHelper()
                    .setPhoto(
                        context = requireContext(),
                        img_url = it?.profileImage?.large,
                        into = binding.profile.authorProfileImg,
                        blurHash = ""
                    )
            }
        }

        setMenu()
    }


    private fun setMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {}

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.log_out, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Log.d("CLICK", "Logout clicked")
                viewModel.logout()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}