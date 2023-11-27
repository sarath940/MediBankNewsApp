package com.example.medibanknewsapp.ui.fragments.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.repoistory.Repository
import com.example.medibanknewsapp.utils.TestCoroutineRule2
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ArticleViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule2()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = mock(Repository::class.java)
        articleViewModel = ArticleViewModel(repository, AppCoroutineDispatchers(testDispatcher))
    }

    @Test
    fun checkSavedStatus() = testCoroutineRule.runBlockingTest {
        val articleTitle = "Test Article"
        `when`(repository.isArticleSaved(articleTitle)).thenReturn(true)
        articleViewModel.checkSavedStatus(articleTitle)
        testCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(articleViewModel.savedStatus.value)
    }

    @Test
    fun toggleSavedStatusShouldSaveArticleWhenNotSavedAndDeleteWhenSaved() = testCoroutineRule.runBlockingTest {
        val article = Article(title = "title")
        `when`(repository.isArticleSaved(article.title ?: "")).thenReturn(false)
        articleViewModel.toggleSavedStatus(article)
        testCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(articleViewModel.savedStatus.value)
        Mockito.lenient().`when`(repository.isArticleSaved(article.title ?: "")).thenReturn(true)
        articleViewModel.toggleSavedStatus(article)
        testCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(articleViewModel.savedStatus.value)
    }

    @After
    fun reset(){

    }
}