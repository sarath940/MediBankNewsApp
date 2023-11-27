package com.example.medibanknewsapp.data.repoistory

import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import retrofit2.Response


interface Repository {
    suspend fun fetchRemoteHeadlines(sources:String): Response<NewsResponse>
    suspend fun fetchRemoteSources():Response<SourcesResponse>
    suspend fun fecthLocalSavedArticles(): List<Article>
    suspend fun deleteArticle(article: Article)
    suspend fun saveArticle(article: Article)
    suspend fun isArticleSaved(articleTitle: String):Boolean
    suspend fun fecthLocalSavedSources(): List<Source>
    suspend fun deleteSource(id: String)
    suspend fun saveSource(source: Source)
    suspend fun isSourceSaved(sourceId: String):Boolean
}