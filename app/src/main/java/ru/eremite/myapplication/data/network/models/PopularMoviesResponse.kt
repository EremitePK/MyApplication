package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.domain.models.Movie

@Serializable
class PopularMoviesResponse {
    @SerialName("page")
    var page = 0

    @SerialName("results")
    private var results: List<MovieNetwork>? = null

    @SerialName("total_results")
    var totalResults = 0

    @SerialName("total_pages")
    var totalPages = 0

    fun getResults(genreList: List<GenreNetwork>): List<Movie> {
        var mutableList = mutableListOf<Movie>()
        movies?.forEach { mutableList.add(it.getMovieModelData(genreList)) }
        return mutableList
    }

    var movies: List<MovieNetwork>? = results
}

