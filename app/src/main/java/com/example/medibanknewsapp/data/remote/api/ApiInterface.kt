package com.example.medibanknewsapp.data.remote.api

import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<SourcesResponse>
}
