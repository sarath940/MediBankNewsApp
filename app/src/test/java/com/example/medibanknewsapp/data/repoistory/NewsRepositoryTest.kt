package com.example.medibanknewsapp.data.repoistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.data.local.LocalDataSource
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import com.example.medibanknewsapp.data.remote.RemoteSource
import com.example.medibanknewsapp.utils.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {
    private lateinit var remoteSource: RemoteSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var newsRepository: NewsRepository

    private val article1 = Article(author = "Author1", description = "Description1")
    private val article2 = Article(author = "Author2", description = "Description2")
    private val article3 = Article(author = "Author3", description = "Description3")

    private val source1 = Source(language = "en1", description = "Description1")
    private val source2 = Source(language = "en2", description = "Description2")
    private val source3 = Source(language = "en3", description = "Description3")


    private val dataArticles = listOf(article1, article2, article3)
    private val dataSources = listOf(source1, source2, source3)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remoteSource = mock(RemoteSource::class.java)
        localDataSource = mock(LocalDataSource::class.java)
        newsRepository = NewsRepository(remoteSource, localDataSource)
    }

    @Test
    fun fetchRemoteHeadlinesShouldReturnHeadlinesFromRemoteDataSource() =
        testCoroutineRule.runBlockingTest {
            val sources = "abc"
            val expectedResponse = Response.success(createMockNewsResponse())
            `when`(remoteSource.fetchHeadlines(sources)).thenReturn(expectedResponse)
            val result = newsRepository.fetchRemoteHeadlines(sources)
            assertEquals(expectedResponse, result)
        }

    @Test
    fun fetchRemoteSourcesShouldReturnSourcesFromRemoteDataSource() =
        testCoroutineRule.runBlockingTest {
            val expectedResponse = Response.success(createMockSourcesResponse())
            `when`(remoteSource.fetchSources()).thenReturn(expectedResponse)
            val result = newsRepository.fetchRemoteSources()
            assertEquals(expectedResponse, result)
        }

    @Test
    fun fecthLocalSavedArticlesShouldReturnLocalSavedArticlesFromLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            `when`(localDataSource.getAllSavedArticles()).thenReturn(dataArticles)
            val fetchLocalSavedArticles = newsRepository.fecthLocalSavedArticles()
            assertEquals(dataArticles,fetchLocalSavedArticles)
        }

    @Test
    fun deleteArticleShouldDeleteSavedArticleFromLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            newsRepository.deleteArticle(article1)
            verify(localDataSource).deleteArticle(article1)
        }

    @Test
    fun saveArticleShouldSaveArticleToLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            newsRepository.saveArticle(article1)
            verify(localDataSource).saveArticle(article1)
        }

    @Test
    fun isArticleSavedShouldCheckArticleIsSavedToLocalDataSourceOrNot() =
        testCoroutineRule.runBlockingTest {
            `when`(localDataSource.isArticleSaved("article1")).thenReturn(true)
            assertEquals(true, newsRepository.isArticleSaved("article1"))
        }

    @Test
    fun fecthLocalSavedSourcesShouldReturnLocalSavedSourcesFromLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            `when`(localDataSource.getAllSavedSources()).thenReturn(dataSources)
            val fecthLocalSavedSources = newsRepository.fecthLocalSavedSources()
            assertEquals(dataSources,fecthLocalSavedSources)
        }

    @Test
    fun deleteSourceShouldDeleteSavedSourceFromLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            newsRepository.deleteSource(source1.id)
            verify(localDataSource).deleteSource(source1.id)
        }

    @Test
    fun saveSourceShouldSaveSourceToLocalDataSource() =
        testCoroutineRule.runBlockingTest {
            newsRepository.saveSource(source1)
            verify(localDataSource).saveSource(source1)
        }

    @Test
    fun isSourceSavedShouldCheckSourceIsSavedToLocalDataSourceOrNot() =
        testCoroutineRule.runBlockingTest {
            `when`(localDataSource.isSourceSaved(source1.id)).thenReturn(true)
            assertEquals(true,newsRepository.isSourceSaved(source1.id))
        }


    private fun createMockNewsResponse(): NewsResponse {
        return NewsResponse(status = "200", articles = dataArticles, totalResults = 3)
    }
    private fun createMockSourcesResponse(): SourcesResponse {
        return SourcesResponse(status = "200", sources = dataSources)
    }
}