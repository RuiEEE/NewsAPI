package dev.ruisantos.criticalnews.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.ruisantos.criticalnews.R
import dev.ruisantos.criticalnews.adapters.HeadlineAdapter
import dev.ruisantos.criticalnews.data.NewsRepositoryImpl
import dev.ruisantos.criticalnews.databinding.FragmentMainBinding
import dev.ruisantos.criticalnews.network.RetrofitCriticalNews
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class MainFragment : Fragment() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val launchSettings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Got back from the settings
        }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
                repository = NewsRepositoryImpl(RetrofitCriticalNews())
            ) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvHeadlines.apply {
                adapter = HeadlineAdapter(mutableListOf()) {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToDetailFragment(
                            it
                        )
                    )
                }
            }

            bAuth.setOnClickListener {
                showBiometricPrompt()
            }
        }

        lifecycleScope.launch {
            viewModel.ui.collect { ui ->
                when (ui) {
                    is MainViewModel.UI.Headlines -> setupHeadlines(ui)
                    MainViewModel.UI.Authentication -> showBiometricPrompt()
                    MainViewModel.UI.Settings -> openSettings()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.checkBiometricAvailability(requireContext())
        }
    }

    private fun setupHeadlines(headlines: MainViewModel.UI.Headlines) {
        binding.apply {
            rvHeadlines.adapter?.let {
                (it as HeadlineAdapter).apply {
                    items = headlines.articles
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun openSettings() {
        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }.also {
            launchSettings.launch(it)
        }
    }

    private fun showBiometricPrompt() {
        // Show fingerprint prompt
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    binding.bAuth.visibility = View.VISIBLE
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    binding.bAuth.visibility = View.GONE
                    viewModel.fetchHeadlines()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.bAuth.visibility = View.VISIBLE
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setSubtitle(getString(R.string.biometric_subtitle))
            .setNegativeButtonText(getString(R.string.biometric_cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

}