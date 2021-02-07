package ru.eremite.myapplication.data.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieGenreDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieGenreEntity: MovieGenreEntity)

    @Query("DELETE FROM movie_genres")
    suspend fun deleteAll()

}