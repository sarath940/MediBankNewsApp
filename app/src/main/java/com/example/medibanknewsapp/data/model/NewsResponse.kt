package com.example.medibanknewsapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article?>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
) {
    @Entity(
        tableName = "article"
    )
    data class Article(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @SerializedName("author")
        val author: String? = null,
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("publishedAt")
        val publishedAt: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("url")
        val url: String? = null,
        @SerializedName("urlToImage")
        val urlToImage: String? = null
    )
}