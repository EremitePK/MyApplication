package ru.eremite.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = DBContract.Actor.TABLE_NAME,
    indices = [Index(DBContract.Actor.COLUMN_NAME_ID)]
)
class ActorEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DBContract.Actor.COLUMN_NAME_ID)
    val _id: Int,

    @ColumnInfo(name = DBContract.Actor.COLUMN_NAME_NAME)
    val title: String,

    @ColumnInfo(name = DBContract.Actor.COLUMN_NAME_PICTURE)
    val picture: String

    )