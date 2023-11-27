package com.example.medibanknewsapp.data.remote

import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.SourcesResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun fetchHeadlines(sources: String): Response<NewsResponse>
    suspend fun fetchSources(): Response<SourcesResponse>
}