import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.repoistory.Repository
import com.example.medibanknewsapp.ui.fragments.saved.SavedNewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SavedNewsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = mock(Repository::class.java)
        viewModel = SavedNewsViewModel(repository, AppCoroutineDispatchers(testDispatcher))
    }
    @Test
    fun deleteSavedArticleShouldDeleteArticleInRepository() = testDispatcher.runBlockingTest {
        val savedArticles = listOf(Article(title = "Title 1"), Article(title = "Title 2"), Article(title = "Title 3"))
        `when`(repository.fecthLocalSavedArticles()).thenReturn(savedArticles)
        viewModel.loadSavedArticles()
        viewModel.deleteSavedArticle(1)
        verify(repository).deleteArticle( Article(title = "Title 2"))
    }

    @Test
    fun loadSavedArticleShouldLoadArticlesInRepository() = testDispatcher.runBlockingTest {
        viewModel.loadSavedArticles()
        verify(repository).fecthLocalSavedArticles()
    }

}
