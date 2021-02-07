package ru.eremite.myapplication.data.db

import android.provider.BaseColumns

object DBContract {
    const val DATABASE_NAME = "Movie.db"

    object Movie {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_MOVIEID = "movieId"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER = "poster"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_MINIMUMAGE = "minimumAge"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_LIKE = "like"
    }

    object Actor {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PICTURE = "picture"
    }

    object Genre {
        const val TABLE_NAME = "genres"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
    }

    object MovieActors {
        const val TABLE_NAME = "movie_actors"

        const val COLUMN_NAME_MOVE_ID = "movieid"
        const val COLUMN_NAME_ACTOR_ID = "actorid"
        const val COLUMN_NAME_NOMERPP = "nomerPP"
    }

    object MovieGenres {
        const val TABLE_NAME = "movie_genres"

        const val COLUMN_NAME_MOVE_ID = "movieid"
        const val COLUMN_NAME_GENRE_ID = "genreid"
    }
}