package com.example.medibanknewsapp.data.local

import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse.Source

interface LocalDataSource {
    //Article Methods
    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    suspend fun getAllSavedArticles(): List<Article>
    suspend fun isArticleSaved(articleTitle: String): Boolean

    // Source methods
    suspend fun saveSource(source: Source)
    suspend fun deleteSource(id: String?)
    suspend fun getAllSavedSources(): List<Source>
    suspend fun isSourceSaved(sourceId: String): Boolean

}
