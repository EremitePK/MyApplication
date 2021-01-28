package ru.eremite.myapplication.data.db.models

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UpdateCacheDAO {
    @Query("SELECT * FROM movies")
    fun getCacheUnderUpdate(): Flow<List<MovieWithActorsGenres>>
}