package ru.eremite.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenresDAO {
    @Query("SELECT * FROM genres ORDER BY _id ASC")
    suspend fun getAll(): List<GenreEntity>

    @Insert
    suspend fun insert(genreEntity: GenreEntity)

    @Query("DELETE FROM genres WHERE _id == :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM genres")
    suspend fun deleteAll()

    @Query("SELECT * FROM genres WHERE _id == :id")
    suspend fun getGenreById(id: Int): GenreEntity


}