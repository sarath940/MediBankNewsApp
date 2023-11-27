package com.example.medibanknewsapp.data.remote

import com.example.medibanknewsapp.data.model.NewsResponse
import com.example.medibanknewsapp.data.model.SourcesResponse
import com.example.medibanknewsapp.data.remote.api.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class RemoteImpl @Inject constructor(private val apiInterface: ApiInterface) : RemoteSource {
    override suspend fun fetchHeadlines(sources:String): Response<NewsResponse> {
        return apiInterface.getHeadlines(sources)
    }

    override suspend fun fetchSources(): Response<SourcesResponse> {
       return apiInterface.getSources()
    }


}