package com.example.medibanknewsapp.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.remote.api.ApiInterface
import com.example.medibanknewsapp.utils.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemoteImplTest{
    private lateinit var apiInterface: ApiInterface
    private val article1 = NewsResponse.Article(author = "Author1", description = "Description1")
    private val article2 = NewsResponse.Article(author = "Author2", description = "Description2")
    private val article3 = NewsResponse.Article(author = "Author3", description = "Description3")

    private val source1 = SourcesResponse.Source(language = "en1", description = "Description1")
    private val source2 = SourcesResponse.Source(language = "en2", description = "Description2")
    private val source3 = SourcesResponse.Source(language = "en3", description = "Description3")


    private val dataArticles = listOf(article1, article2, article3)
    private val dataSources = listOf(source1, source2, source3)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var remoteImpl: RemoteImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        apiInterface = Mockito.mock(ApiInterface::class.java)
        remoteImpl= RemoteImpl(apiInterface)
    }

    @Test
    fun fetchHeadlinesShouldReturnHeadlinesFromApiInterface() =
        testCoroutineRule.runBlockingTest {
            val sources = "abc"
            val expectedResponse = Response.success(createMockNewsResponse())
            Mockito.`when`(apiInterface.getHeadlines(sources)).thenReturn(expectedResponse)
            val result = remoteImpl.fetchHeadlines(sources)
            TestCase.assertEquals(expectedResponse, result)
        }

    @Test
    fun fetchRemoteSourcesShouldReturnSourcesFromRemoteDataSource() =
        testCoroutineRule.runBlockingTest {
            val expectedResponse = Response.success(createMockSourcesResponse())
            Mockito.`when`(apiInterface.getSources()).thenReturn(expectedResponse)
            val result = remoteImpl.fetchSources()
            TestCase.assertEquals(expectedResponse, result)
        }

    private fun createMockNewsResponse(): NewsResponse {
        return NewsResponse(status = "200", articles = dataArticles, totalResults = 3)
    }
    private fun createMockSourcesResponse(): SourcesResponse {
        return SourcesResponse(status = "200", sources = dataSources)
    }
}