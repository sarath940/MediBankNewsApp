package com.example.medibanknewsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medibanknewsapp.data.model.SourcesResponse.Source

@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: Source): Long

    @Query("SELECT * FROM source")
    suspend fun getAllSources(): List<Source>

    @Query("SELECT COUNT(*) FROM source WHERE id = :sourceId")
    suspend fun isSourceSaved(sourceId: String): Boolean

    @Query("DELETE FROM source WHERE id = :id")
    suspend fun deleteSourceById(id: String)
}