package com.example.medibanknewsapp.ui.fragments.headlines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.NewsResponse.Article
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HeadlinesNewsViewModelTest{
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: HeadlinesNewsViewModel
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = Mockito.mock(Repository::class.java)
        viewModel = HeadlinesNewsViewModel(repository, AppCoroutineDispatchers(testDispatcher))
    }

    @Test
    fun loadTopHeadlinesShouldCallFetchRemoteHeadinesFromRepoistory() = testDispatcher.runBlockingTest {
        val mockSourceId = "source"
        val mockArticleList = listOf(Article(title = "Mock Article"))
        val mockResponse = NewsResponse(articles = mockArticleList)
        `when`(repository.fecthLocalSavedSources()).thenReturn(listOf(Source(id = mockSourceId)))
        `when`(repository.fetchRemoteHeadlines(mockSourceId)).thenReturn(Response.success(mockResponse))
        viewModel.loadTopHeadlines()
        verify(repository).fecthLocalSavedSources()
        verify(repository).fetchRemoteHeadlines(mockSourceId)
    }
}