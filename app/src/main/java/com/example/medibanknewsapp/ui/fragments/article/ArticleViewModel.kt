
package com.example.medibanknewsapp.ui.fragments.article
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.repoistory.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: Repository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ViewModel(),Repository by repository {
    private val _article = MutableStateFlow(Article())
    var article: StateFlow<Article> = _article

    private val _savedStatus = MutableStateFlow<Boolean>(false)
    val savedStatus: StateFlow<Boolean> = _savedStatus

    fun checkSavedStatus(articleTitle: String) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            _savedStatus.value = repository.isArticleSaved(articleTitle)
        }
    }

    fun toggleSavedStatus(article: Article) {
        viewModelScope.launch {
            if (savedStatus.value) {
                repository.deleteArticle(article)
                _savedStatus.value = false
            } else {
                repository.saveArticle(article)
                _savedStatus.value = true
            }
        }
    }
}
