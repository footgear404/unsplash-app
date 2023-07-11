package com.semenchuk.unsplash.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.semenchuk.unsplash.R
import com.semenchuk.unsplash.app.App
import com.semenchuk.unsplash.databinding.FragmentAuthorizationBinding
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels { App.appComponent.auth() }
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    //    private var _getPrefs: SharedPreferences? = null
//    private val getPrefs get() = _getPrefs!!

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authBtn: Button = view.findViewById(R.id.btnAuth)

        authBtn.setOnClickListener {
            viewModel.openLoginPage()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingMutableStateFlow.collect {
                updateIsLoading(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEventChannel.collect {
                Snackbar.make(authBtn, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openAuthPageEventChannel.collect {
                Log.d("TAG", "Intent: ${it.data}")
                openAuthPage(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authSuccessEventChannel.collect {
                viewModel.saveProfileToDb()
//                findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.status.collect {
                when (it) {
                    true -> findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                    false -> Log.d("TAG", "status: $it")
                }
            }
        }
    }

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        when (isLoading) {
            true -> binding.progress.visibility = View.VISIBLE
            false -> binding.progress.visibility = View.GONE
        }
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> viewModel.onAuthCodeFailed(exception)
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null ->
                viewModel.onAuthCodeReceived(tokenExchangeRequest)
        }
    }
}