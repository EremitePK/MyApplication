package ru.eremite.myapplication.ViewModel

import ru.eremite.myapplication.data.ModelData

sealed class PresentationModelData(open val id: Int) {
    data class ActorPresentation(
        override val id: Int,
        val name: String,
        val picture: String
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is ActorPresentation -> id == other.id
                else -> false
            }
        }

        constructor(actor: ModelData.Actor) : this(actor.id, actor.name, actor.picture)
    }

    data class GenrePresentation(
        override val id: Int,
        val name: String,
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is GenrePresentation -> id == other.id
                else -> false
            }
        }

        constructor(genre: ModelData.Genre) : this(genre.id, genre.name)
    }

    data class MoviePresentation(
        override val id: Int = -1,
        val title: String = "",
        val overview: String = "",
        val poster: String? = "",
        val backdrop: String? = "",
        val ratings: Float = 0F,
        val numberOfRatings: Int = -1,
        var minimumAge: String = "",
        var runtime: Int = -1,
        var like: Boolean = false,
        val genres: List<PresentationModelData.GenrePresentation> = listOf()
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is MoviePresentation -> ((id == other.id) && (like == other.like))
                else -> false
            }
        }

        constructor(movie: ModelData.Movie) : this(movie.id,
            movie.title,
            movie.overview,
            movie.poster,
            movie.backdrop,
            movie.ratings,
            movie.numberOfRatings,
            movie.minimumAge,
            movie.runtime,
            movie.like,
            movie.genres.map { GenrePresentation(it) })

        fun getGeners(): String {
            var stringGenre = "";
            genres.forEach {
                if (stringGenre != "") {
                    stringGenre = "$stringGenre, "
                }
                stringGenre += it.name
            }
            return stringGenre
        }
    }
}