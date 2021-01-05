package ru.eremite.myapplication.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class MovieWithActorsGenres(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = DBContract.Movie.COLUMN_NAME_MOVIEID,
        entity = ActorEntity::class,
        entityColumn = DBContract.Actor.COLUMN_NAME_ID,
        associateBy = Junction(
            value = MovieActorEntity::class,
            parentColumn = DBContract.MovieActors.COLUMN_NAME_MOVE_ID,
            entityColumn = DBContract.MovieActors.COLUMN_NAME_ACTOR_ID,
        )
    )
    val actors: List<ActorEntity>,
    @Relation(
        parentColumn = DBContract.Movie.COLUMN_NAME_MOVIEID,
        entity = GenreEntity::class,
        entityColumn = DBContract.Genre.COLUMN_NAME_ID,
        associateBy = Junction(
            value = MovieGenreEntity::class,
            parentColumn = DBContract.MovieGenres.COLUMN_NAME_MOVE_ID,
            entityColumn = DBContract.MovieGenres.COLUMN_NAME_GENRE_ID
        )
    )
    val genres: List<GenreEntity>
)