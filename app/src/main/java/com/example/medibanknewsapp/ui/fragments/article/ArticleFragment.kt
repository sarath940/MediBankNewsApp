package com.example.medibanknewsapp.ui.fragments.article

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.medibanknewsapp.MainActivityViewModel
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.databinding.FragmentArticleBinding
import com.example.medibanknewsapp.util.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var  article: Article
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)
         article = activityViewModel.selectedArticle.value!!
        binding.webView.apply {
            webViewClient = WebViewClient()
            activityViewModel.selectedArticle.value?.url?.let { loadUrl(it) }
        }
        article?.title?.let { viewModel.checkSavedStatus(it) }

        binding.back.setSafeOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.title.text = article.title

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedStatus.collect { isArticleSaved ->
                    setFabIcon(isArticleSaved)
                }
            }
        }
        binding.fab.setSafeOnClickListener {
            article.let { it1 -> viewModel.toggleSavedStatus(it1) }
        }
    }

    override fun onPause() {
        super.onPause()
        article.let { it1 -> viewModel.toggleSavedStatus(it1) }
    }

    private fun setFabIcon(isArticleSaved: Boolean) {
        val fabIcon = if (isArticleSaved) Color.rgb(255, 50, 50) else Color.rgb(255, 255, 255)
        binding.fab.imageTintList = ColorStateList.valueOf(fabIcon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
