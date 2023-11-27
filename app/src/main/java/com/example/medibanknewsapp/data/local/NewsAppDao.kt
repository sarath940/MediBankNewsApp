package com.example.medibanknewsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medibanknewsapp.data.model.NewsResponse.*

@Dao
interface NewsAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("SELECT * FROM article")
    fun getAllArticles(): List<Article>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT COUNT(*) FROM article WHERE title = :articleTitle")
    suspend fun isArticleSaved(articleTitle: String): Boolean
}