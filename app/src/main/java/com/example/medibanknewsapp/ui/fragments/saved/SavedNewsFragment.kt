
package com.example.medibanknewsapp.ui.fragments.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medibanknewsapp.MainActivityViewModel
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.databinding.FragmentSavedNewsBinding
import com.example.medibanknewsapp.ui.adapters.ArticlesNewsAdapter
import com.example.medibanknewsapp.util.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedNewsViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val adapter: ArticlesNewsAdapter by lazy {
        ArticlesNewsAdapter { article ->
            activityViewModel.setSelectedArticle(article)
            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedNewsBinding.bind(view)

        setupRecyclerView()
        viewModel.loadSavedArticles()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedNews.collect { data ->
                    showContent()
                    when {
                        data.isEmpty() -> showNoSavedNews()
                        else -> adapter.submitList(data)
                    }
                }
            }
        }
        setupSwipeToDelete()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSavedArticles()
    }

    private fun setupRecyclerView() {
        binding.rvSavedNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSavedNews.adapter = adapter
    }

    private fun showNoSavedNews() {
        binding.rvSavedNews.visibility = View.GONE
        binding.tvNoSavedNews.visibility = View.VISIBLE
    }

    private fun setupSwipeToDelete() {
        val swipeToDeleteCallback = SwipeToDeleteCallback { position ->
            viewModel.deleteSavedArticle(position)
            adapter.notifyItemRemoved(position)
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews)
    }

    private fun showContent() {
        binding.rvSavedNews.visibility = View.VISIBLE
        binding.tvNoSavedNews.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
