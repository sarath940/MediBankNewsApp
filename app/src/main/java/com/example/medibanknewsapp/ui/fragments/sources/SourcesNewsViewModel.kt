package com.example.medibanknewsapp.ui.fragments.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.Resource
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import com.example.medibanknewsapp.data.repoistory.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SourcesNewsViewModel @Inject constructor(
    private val repository: Repository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val _sourcesNews = MutableStateFlow<Resource<List<Source>>>(Resource.Loading())
    val sourcesNews: StateFlow<Resource<List<Source>>> = _sourcesNews.asStateFlow()

    private val _localSavedSourcesNews = MutableStateFlow<List<Source>>(emptyList())
    val localSavedSourcesNews: StateFlow<List<Source>> = _localSavedSourcesNews.asStateFlow()

    fun loadSources() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            try {
                val result = repository.fetchRemoteSources()
                _sourcesNews.value = Resource.Success(data = result.body()?.sources?.filter { it?.language == "en"} as List<Source>? ?: emptyList())
            } catch (e: IOException) {
                _sourcesNews.value = Resource.Error("Connection or Network Error", null)
            } catch (e: Exception) {
                _sourcesNews.value = Resource.Error("Unexpected Error Caused", null)
            }
        }
    }

    private fun saveSourceToLocalDatabase(source: Source) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            repository.saveSource(source)
        }
    }

    private suspend fun deleteSource(source: Source) {

            source.id.let { repository.deleteSource(it) }

    }

    fun isSourceSaved(source: Source) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            val result = source.id.let { repository.isSourceSaved(sourceId = it) }
            if (result) deleteSource(source)
            else saveSourceToLocalDatabase(source)
            getAllSavedSources()
        }
    }
    fun getAllSavedSources() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            try {
                val result = repository.fecthLocalSavedSources()
                _localSavedSourcesNews.value =result

            } catch (e: Exception) {
                _localSavedSourcesNews.value = emptyList()
            }
        }
    }


}
