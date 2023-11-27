
package com.example.medibanknewsapp.ui.fragments.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.repoistory.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val repository: Repository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ViewModel() ,Repository by repository{

    private val _savedNews = MutableStateFlow<List<Article>>(emptyList())
    val savedNews: StateFlow<List<Article>> = _savedNews.asStateFlow()

      fun loadSavedArticles() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            try {
                _savedNews.value = repository.fecthLocalSavedArticles()
            } catch (e: IOException) {
                _savedNews.value = emptyList()
            } catch (e: Exception) {
                _savedNews.value = emptyList()
            }
        }
    }

    fun deleteSavedArticle(position: Int) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            try {
                val deletedArticle = _savedNews.value[position]
                deletedArticle.let { repository.deleteArticle(it) }
                _savedNews.value = _savedNews.value.toMutableList().apply {
                    removeAt(position)
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}
