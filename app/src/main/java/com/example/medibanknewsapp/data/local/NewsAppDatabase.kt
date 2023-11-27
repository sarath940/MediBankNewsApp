package com.example.medibanknewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.data.model.SourcesResponse.Source

@Database(entities = [Article::class, Source::class], version = 1)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun newsAppDao(): NewsAppDao
    abstract fun sourcesDao():SourcesDao

    companion object {
        const val DATABASE_NAME: String = "news_app_db"
    }
}