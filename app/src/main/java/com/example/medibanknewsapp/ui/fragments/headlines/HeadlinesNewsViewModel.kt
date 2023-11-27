package com.example.medibanknewsapp.ui.fragments.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.Resource
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
class HeadlinesNewsViewModel @Inject constructor(
    private val repository: Repository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ViewModel(),Repository by repository {

    private val _headlinesNews = MutableStateFlow<Resource<List<Article>>>(Resource.Loading())
    val headlinesNews: StateFlow<Resource<List<Article>>> = _headlinesNews.asStateFlow()

    fun loadTopHeadlines() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            try {
                val result = repository.fetchRemoteHeadlines(
                    repository.fecthLocalSavedSources().map { it.id }.joinToString(",")
                )
                if(result.body()?.articles?.isEmpty()==true){
                    _headlinesNews.value = Resource.Error("The List is Empty", null)
                }else{
                    _headlinesNews.value = Resource.Success(data = result.body()?.articles as List<Article>? ?: emptyList())
                }

            } catch (e: IOException) {
                _headlinesNews.value = Resource.Error("Connection or Network Error", null)
            } catch (e: Exception) {
                _headlinesNews.value = Resource.Error("Unexpected Error Caused", null)
            }
        }
    }
}
