package ru.eremite.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = DBContract.Movie.TABLE_NAME,
    indices = [Index(DBContract.Movie.COLUMN_NAME_ID)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_ID)
    val _id: Int=0,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_MOVIEID)
    val movieId: Int,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_OVERVIEW)
    val overview: String,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_POSTER)
    val poster: String,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_BACKDROP)
    val backdrop: String,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_RATINGS)
    val ratings: Float,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_MINIMUMAGE)
    val minimumAge: String,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_RUNTIME)
    val runtime: Int,

    @ColumnInfo(name = DBContract.Movie.COLUMN_NAME_LIKE)
    val like: Boolean
)