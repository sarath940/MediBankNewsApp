package com.example.medibanknewsapp.di

import android.content.Context
import androidx.room.Room
import com.example.medibanknewsapp.data.local.NewsAppDao
import com.example.medibanknewsapp.data.local.NewsAppDatabase
import com.example.medibanknewsapp.data.local.SourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): NewsAppDatabase {
        return Room
            .databaseBuilder(
                context,
                NewsAppDatabase::class.java,
                NewsAppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDAO(database: NewsAppDatabase): NewsAppDao {
        return database.newsAppDao()
    }

    @Singleton
    @Provides
    fun provideSourceDAO(database: NewsAppDatabase): SourcesDao {
        return database.sourcesDao()
    }
}
