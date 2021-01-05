package ru.eremite.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, ActorEntity::class, GenreEntity::class, MovieActorEntity::class, MovieGenreEntity::class],
    version = 9
)
abstract class DataBase : RoomDatabase() {
    abstract val moviesDAO: MoviesDAO
    abstract val actorsDAO: ActorsDAO
    abstract val genresDAO: GenresDAO
    abstract val movieGenresDAO: MovieGenreDAO
    abstract val movieActorsDAO: MovieActorDAO

    companion object {

        fun create(applicationContext: Context): DataBase = Room.databaseBuilder(
            applicationContext,
           DataBase::class.java,
            DBContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}