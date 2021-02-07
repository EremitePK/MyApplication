package ru.eremite.myapplication.data.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM movies ORDER BY _id")
    suspend fun getAll(): List<MovieWithActorsGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("DELETE FROM movies WHERE movieId == :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies WHERE movieId == :id")
    suspend fun getMovieById(id: Int): MovieWithActorsGenres
}