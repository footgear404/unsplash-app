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
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.databinding.FragmentProfileBinding
import com.semenchuk.unsplash.utils.GlideImageHelper
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
            viewModel.profile.collect {
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = it?.username
                binding.userName.text = it?.firstName + " " + it?.lastName
                binding.nickname.text = "@" + it?.username
                binding.location.text = it?.location
                binding.email.text = it?.email
                binding.authorDescription.text = it?.bio

                GlideImageHelper()
                    .setPhoto(
                        context = requireContext(),
                        img_url = it?.profileImage?.large,
                        into = binding.authorProfileImg,
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
                Log.d("TAG", "onMenuItemSelected: ")
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}