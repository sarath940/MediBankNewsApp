package com.example.medibanknewsapp

import androidx.lifecycle.ViewModel
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.repoistory.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel  @Inject constructor(
    private val newsRepository: NewsRepository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
)  : ViewModel() {
    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle
    fun setSelectedArticle(article: Article) {
        _selectedArticle.value = article
    }
}