package ru.eremite.myapplication.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import ru.eremite.myapplication.data.db.DBContract

@Entity(
    tableName = DBContract.MovieGenres.TABLE_NAME,
    primaryKeys = [DBContract.MovieGenres.COLUMN_NAME_MOVE_ID, DBContract.MovieGenres.COLUMN_NAME_GENRE_ID],
    indices = [Index(DBContract.MovieGenres.COLUMN_NAME_MOVE_ID), Index(DBContract.MovieGenres.COLUMN_NAME_GENRE_ID)])
data class MovieGenreEntity(

    @ColumnInfo(name = DBContract.MovieGenres.COLUMN_NAME_MOVE_ID)
    val movieId: Int,

    @ColumnInfo(name = DBContract.MovieGenres.COLUMN_NAME_GENRE_ID)
    val genreId: Int
)