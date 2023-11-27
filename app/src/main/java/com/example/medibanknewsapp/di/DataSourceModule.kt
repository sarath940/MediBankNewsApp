package com.example.medibanknewsapp.di

import com.example.medibanknewsapp.data.local.LocalDataSource
import com.example.medibanknewsapp.data.local.LocalDataSourceImpl
import com.example.medibanknewsapp.data.remote.RemoteImpl
import com.example.medibanknewsapp.data.remote.RemoteSource
import com.example.medibanknewsapp.data.repoistory.NewsRepository
import com.example.medibanknewsapp.data.repoistory.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideDataSource(localDataSource: LocalDataSourceImpl?): LocalDataSource?

    @Binds
    abstract fun provideRepoImpl(repo: NewsRepository?): Repository?

    @Binds
    abstract fun provideRemoteImpl(remote: RemoteImpl?): RemoteSource?
}