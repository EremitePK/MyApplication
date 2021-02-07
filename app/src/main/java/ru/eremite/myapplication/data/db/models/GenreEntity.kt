package ru.eremite.myapplication.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import ru.eremite.myapplication.data.db.DBContract

@Entity(
    tableName = DBContract.Genre.TABLE_NAME,
    primaryKeys = [DBContract.Genre.COLUMN_NAME_ID],
    indices = [Index(DBContract.Genre.COLUMN_NAME_ID)]
)
class GenreEntity(

    @ColumnInfo(name = DBContract.Genre.COLUMN_NAME_ID)
    val _id: Int,

    @ColumnInfo(name = DBContract.Genre.COLUMN_NAME_TITLE)
    val title: String,
)
