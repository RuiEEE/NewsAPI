package dev.ruisantos.criticalnews.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.ruisantos.criticalnews.adapters.HeadlineAdapter
import dev.ruisantos.criticalnews.data.NewsRepositoryImpl
import dev.ruisantos.criticalnews.databinding.FragmentMainBinding
import dev.ruisantos.criticalnews.network.RetrofitCriticalNews
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

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
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                }
            }
        }

        lifecycleScope.launch {
            viewModel.ui.collect { ui ->
                when (ui) {
                    is MainViewModel.UI.Headlines -> setupHeadlines(ui)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchHeadlines()
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

}