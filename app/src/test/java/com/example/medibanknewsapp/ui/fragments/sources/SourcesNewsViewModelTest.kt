package com.example.medibanknewsapp.ui.fragments.sources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import com.example.medibanknewsapp.data.repoistory.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SourcesNewsViewModelTest{
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: SourcesNewsViewModel
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = Mockito.mock(Repository::class.java)
        viewModel = SourcesNewsViewModel(repository, AppCoroutineDispatchers(testDispatcher))
    }
    @Test
    fun loadSourcesShouldCallFetchRemotesSourcesFromRepoistory() = testDispatcher.runBlockingTest {
        val mockSourceList = listOf(Source(description = "Mock Article"))
        val mockResponse = SourcesResponse(sources = mockSourceList)
        Mockito.`when`(repository.fetchRemoteSources()).thenReturn(Response.success(mockResponse))
        viewModel.loadSources()
        Mockito.verify(repository).fetchRemoteSources()
    }
    @Test
    fun isSourceSavedShouldCallIsSourceSavedFromRepoistory() = testDispatcher.runBlockingTest {
       val sourceId= "sourceId"
        viewModel.isSourceSaved(sourceId)
        Mockito.verify(repository).isSourceSaved(sourceId)
    }

    @Test
    fun getAllSavedSourcesShouldCallgetAllSavedSourcesFromRepoistory() = testDispatcher.runBlockingTest {
        viewModel.getAllSavedSources()
        Mockito.verify(repository).fecthLocalSavedSources()
    }
}