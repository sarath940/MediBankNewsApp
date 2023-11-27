package com.example.medibanknewsapp.ui.fragments.sources

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.data.Resource
import com.example.medibanknewsapp.databinding.FragmentSourceNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourcesNewsFragment : Fragment(R.layout.fragment_source_news) {

    private var _binding: FragmentSourceNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SourcesNewsViewModel by viewModels()

    private val adapter: SourcesNewsAdapter by lazy {
        SourcesNewsAdapter { source ->
            viewModel.isSourceSaved(source)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSourceNewsBinding.bind(view)

        binding.rvSourceNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSourceNews.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sourcesNews.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            showContent()
                            adapter.submitList(
                                resource.data ?: emptyList(),
                                viewModel.localSavedSourcesNews.value
                            )
                        }

                        is Resource.Error -> {
                            showError(resource.message)
                        }

                        is Resource.Loading -> {
                            showLoading()
                        }
                    }
                }
            }
        }
        viewModel.getAllSavedSources()
        viewModel.loadSources()
    }

    private fun showLoading() {
        binding.shimmerLayout.startShimmer()
        binding.itemErrorMessage.root.visibility = View.GONE
        binding.rvSourceNews.visibility = View.GONE
    }

    private fun showContent() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.itemErrorMessage.root.visibility = View.GONE
        binding.rvSourceNews.visibility = View.VISIBLE
    }

    private fun showError(error: String?) {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvSourceNews.visibility = View.GONE
        binding.itemErrorMessage.root.visibility = View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = error.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
