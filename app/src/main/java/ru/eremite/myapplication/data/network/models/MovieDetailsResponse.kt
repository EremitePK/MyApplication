package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.network.BaseDataTMDb
import ru.eremite.myapplication.domain.models.Genre
import ru.eremite.myapplication.domain.models.Movie

@Serializable
class MovieDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String = "",
    @SerialName("overview")
    val overview: String? = "",
    @SerialName("poster_path")
    val poster: String? = "",
    @SerialName("backdrop_path")
    val backdrop: String? = "",
    @SerialName("vote_average")
    val ratings: Float = 0F,
    @SerialName("runtime")
    val runtime: Int? = 0,
    @SerialName("vote_count")
    val numberOfRatings: Int = 0,
    @SerialName("genres")
    val genre: List<GenreNetwork>
) {
    fun getResults(): Movie {
        var genreMovie = mutableListOf<Genre>()
        genre.forEach { genreMovie.add(Genre(it.id, it.name)) }
        return Movie(
            id,
            title,
            overview ?: "",
            BaseDataTMDb.PATH_POSTER + poster,
            BaseDataTMDb.PATH_BACKDROP + backdrop,
            ratings,
            numberOfRatings,
            "",
            runtime ?: 0,
            false,
            genreMovie
        )
    }
}