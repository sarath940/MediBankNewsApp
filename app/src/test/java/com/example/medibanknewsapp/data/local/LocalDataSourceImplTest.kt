package com.example.medibanknewsapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.utils.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceImplTest{
    private lateinit var newsAppDao: NewsAppDao
    private lateinit var sourcesDao: SourcesDao
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
    private lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsAppDao = Mockito.mock(NewsAppDao::class.java)
        sourcesDao = Mockito.mock(SourcesDao::class.java)
        localDataSourceImpl= LocalDataSourceImpl(newsAppDao,sourcesDao)
    }

    @Test
    fun fecthLocalSavedArticlesShouldReturnLocalSavedArticlesFromNewsAppDao() =
        testCoroutineRule.runBlockingTest {
            `when`(newsAppDao.getAllArticles()).thenReturn(dataArticles)
            val fetchLocalSavedArticles = localDataSourceImpl.getAllSavedArticles()
            TestCase.assertEquals(dataArticles, fetchLocalSavedArticles)
        }

    @Test
    fun deleteArticleShouldDeleteSavedArticleFromNewsAppDao() =
        testCoroutineRule.runBlockingTest {
            localDataSourceImpl.deleteArticle(article1)
            verify(newsAppDao).deleteArticle(article1)
        }

    @Test
    fun saveArticleShouldSaveArticleToNewsAppDao() =
        testCoroutineRule.runBlockingTest {
            localDataSourceImpl.saveArticle(article1)
            verify(newsAppDao).insertArticle(article1)
        }

    @Test
    fun isArticleSavedShouldCheckArticleIsSavedToNewsAppDaoOrNot() =
        testCoroutineRule.runBlockingTest {
            `when`(newsAppDao.isArticleSaved("article1")).thenReturn(true)
            TestCase.assertEquals(true, localDataSourceImpl.isArticleSaved("article1"))
        }

    @Test
    fun fecthLocalSavedSourcesShouldReturnLocalSavedSourcesFromSourcesDao() =
        testCoroutineRule.runBlockingTest {
            `when`(sourcesDao.getAllSources()).thenReturn(dataSources)
            val fecthLocalSavedSources = localDataSourceImpl.getAllSavedSources()
            TestCase.assertEquals(dataSources, fecthLocalSavedSources)
        }

    @Test
    fun deleteSourceShouldDeleteSavedSourceFromSourcesDao() =
        testCoroutineRule.runBlockingTest {
            localDataSourceImpl.deleteSource(source1.id)
            verify(sourcesDao).deleteSourceById(source1.id)
        }

    @Test
    fun saveSourceShouldSaveSourceToSourcesDao() =
        testCoroutineRule.runBlockingTest {
            localDataSourceImpl.saveSource(source1)
            verify(sourcesDao).insertSource(source1)
        }

    @Test
    fun isSourceSavedShouldCheckSourceIsSavedToSourcesDaoOrNot() =
        testCoroutineRule.runBlockingTest {
            `when`(sourcesDao.isSourceSaved(source1.id)).thenReturn(true)
            TestCase.assertEquals(true, localDataSourceImpl.isSourceSaved(source1.id))
        }
}