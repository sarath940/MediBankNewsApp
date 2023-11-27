package com.example.medibanknewsapp.data.local


import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val newsAppDao: NewsAppDao,
    private val sourceDao: SourcesDao
) :
    LocalDataSource {
    override suspend fun saveArticle(article: Article) {
        newsAppDao.insertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsAppDao.deleteArticle(article)
    }

    override suspend fun getAllSavedArticles(): List<Article> {
        return newsAppDao.getAllArticles()
    }

    override suspend fun isArticleSaved(articleTitle: String): Boolean {
        return newsAppDao.isArticleSaved(articleTitle)
    }

    override suspend fun saveSource(source: Source) {
        sourceDao.insertSource(source)
    }

    override suspend fun deleteSource(id: String?) {
        sourceDao.deleteSourceById(id ?: "")
    }

    override suspend fun getAllSavedSources(): List<Source> {
        return sourceDao.getAllSources()
    }

    override suspend fun isSourceSaved(sourceId: String): Boolean {
        return sourceDao.isSourceSaved(sourceId)
    }


}