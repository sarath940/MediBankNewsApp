package com.example.medibanknewsapp.data.repoistory


import com.example.medibanknewsapp.data.local.LocalDataSource
import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import com.example.medibanknewsapp.data.remote.RemoteSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localDataSource: LocalDataSource,
) : Repository {
    override suspend fun fetchRemoteHeadlines(sources: String): Response<NewsResponse> {
        return remoteSource.fetchHeadlines(sources)
    }

    override suspend fun fetchRemoteSources(): Response<SourcesResponse> {
        return remoteSource.fetchSources()
    }

    override suspend fun fecthLocalSavedArticles(): List<Article> {
        return localDataSource.getAllSavedArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        localDataSource.deleteArticle(article)
    }

    override suspend fun saveArticle(article: Article) {
        localDataSource.saveArticle(article)
    }

    override suspend fun isArticleSaved(articleTitle: String): Boolean {
        return localDataSource.isArticleSaved(articleTitle)
    }

    override suspend fun fecthLocalSavedSources(): List<Source> {
       return localDataSource.getAllSavedSources()
    }

    override suspend fun deleteSource(id: String) {
        localDataSource.deleteSource(id)
    }

    override suspend fun saveSource(source: Source) {
       localDataSource.saveSource(source)
    }

    override suspend fun isSourceSaved(sourceId: String): Boolean {
        return  localDataSource.isSourceSaved(sourceId)
    }


}