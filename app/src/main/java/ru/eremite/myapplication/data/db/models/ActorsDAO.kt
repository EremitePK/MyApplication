package ru.eremite.myapplication.data.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActorsDAO {
    @Query("SELECT * FROM actors ORDER BY _id ASC")
    suspend fun getAll(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actorEntity: ActorEntity)

    @Query("DELETE FROM actors WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM actors")
    suspend fun deleteAll()

    @Query("SELECT * FROM actors WHERE _id == :id")
    fun getActorById(id: Int): ActorEntity
}