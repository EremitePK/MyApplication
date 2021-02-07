package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.network.BaseDataTMDb
import ru.eremite.myapplication.domain.models.Genre
import ru.eremite.myapplication.domain.models.Movie

@Serializable
data class MovieNetwork(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("title")
    val title: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("poster_path")
    val poster: String? = "",
    @SerialName("backdrop_path")
    val backdrop: String? = "",
    @SerialName("vote_average")
    val ratings: Float = 0F,
    @SerialName("vote_count")
    val numberOfRatings: Int = -1,
    @SerialName("genre_ids")
    val genre: List<Int>,
) {
    fun getMovieModelData(genreList: List<GenreNetwork>): Movie {
        var ganreList = mutableListOf<Genre>()
        genre.forEach { idGenre ->
            genreList.find { it.id == idGenre }
                ?.let { ganreList.add(Genre(it.id, it.name)) }
        }
        return Movie(
            id,
            title,
            overview,
            BaseDataTMDb.PATH_POSTER + poster,
            BaseDataTMDb.PATH_BACKDROP + backdrop,
            ratings,
            numberOfRatings,
            "",
            0,
            false,
            ganreList
        )
    }
}