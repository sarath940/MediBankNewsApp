package com.example.medibanknewsapp.data.model


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources")
    val sources: List<Source?>? = null,
    @SerializedName("status")
    val status: String? = null
) {
    @Entity(
        tableName = "source"
    )
    data class Source(
        @SerializedName("category")
        val category: String? = null,
        @SerializedName("country")
        val country: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @NonNull
        @PrimaryKey
        @SerializedName("id")
        val id: String = "",
        @SerializedName("language")
        val language: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("url")
        val url: String? = null
    )
}