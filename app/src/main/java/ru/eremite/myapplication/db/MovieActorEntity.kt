package ru.eremite.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = DBContract.MovieActors.TABLE_NAME,
    primaryKeys = [DBContract.MovieActors.COLUMN_NAME_MOVE_ID, DBContract.MovieActors.COLUMN_NAME_ACTOR_ID],
    indices = [Index(DBContract.MovieActors.COLUMN_NAME_MOVE_ID), Index(DBContract.MovieActors.COLUMN_NAME_ACTOR_ID)]
)
data class MovieActorEntity(

    @ColumnInfo(name = DBContract.MovieActors.COLUMN_NAME_MOVE_ID)
    val movieId: Int,

    @ColumnInfo(name = DBContract.MovieActors.COLUMN_NAME_ACTOR_ID)
    val actorId: Int,

    @ColumnInfo(name = DBContract.MovieActors.COLUMN_NAME_NOMERPP)
    val nomerPP: Int
)