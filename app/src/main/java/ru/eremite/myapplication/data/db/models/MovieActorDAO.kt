package ru.eremite.myapplication.data.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieActorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieActorEntity: MovieActorEntity)

    @Query("DELETE FROM movie_actors")
    suspend fun deleteAll()

    @Query("DELETE FROM movie_actors WHERE movieId == :movieId")
    suspend fun deleteActorsMovie(movieId: Int)

    @Query("SELECT * FROM movie_actors WHERE movieId == :movieId AND actorid == :actorId ORDER BY nomerPP ")
    suspend fun getMovieActorById(movieId: Int, actorId: Int): MovieActorEntity

}