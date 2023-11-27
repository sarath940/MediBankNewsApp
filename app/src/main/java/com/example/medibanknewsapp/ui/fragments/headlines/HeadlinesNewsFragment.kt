package com.example.medibanknewsapp.ui.fragments.headlines

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medibanknewsapp.MainActivityViewModel
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.data.Resource
import com.example.medibanknewsapp.databinding.FragmentHeadlineNewsBinding
import com.example.medibanknewsapp.ui.adapters.ArticlesNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeadlinesNewsFragment : Fragment(R.layout.fragment_headline_news) {

    private var _binding: FragmentHeadlineNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeadlinesNewsViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    private val adapter: ArticlesNewsAdapter by lazy {
        ArticlesNewsAdapter { article ->
            activityViewModel.setSelectedArticle(article)
            val action = HeadlinesNewsFragmentDirections.actionHeadlinesNewsFragmentToMyDialogFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeadlineNewsBinding.bind(view)

        binding.rvHeadlineNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeadlineNews.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.headlinesNews.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            showContent()
                            if (resource.data?.isEmpty() == true)
                                showError(getString(R.string.please_select_items)) else
                                adapter.submitList(resource.data ?: emptyList())
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
        viewModel.loadTopHeadlines()
    }

    private fun showLoading() {
        binding.shimmerLayout.startShimmer()
        binding.itemErrorMessage.root.visibility=View.GONE
        binding.rvHeadlineNews.visibility = View.GONE
    }

    private fun showContent() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.itemErrorMessage.root.visibility=View.GONE
        binding.rvHeadlineNews.visibility = View.VISIBLE
    }

    private fun showError(error: String?) {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvHeadlineNews.visibility = View.GONE
        binding.itemErrorMessage.root.visibility=View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = error.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
