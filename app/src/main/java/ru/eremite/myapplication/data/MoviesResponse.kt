package ru.eremite.myapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.ModelData.Movie
import ru.eremite.myapplication.utils.BaseURL

@Serializable
class MoviesResponse {
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

    var movies: List<MovieNetwork>?
        get() = results
        set(results) {
            this.results = results
        }

    fun setResults(results: List<MovieNetwork>?) {
        this.results = results
    }
}

@Serializable
class GenresResponse {
    @SerialName("genres")
    private var genres: List<GenreNetwork> = mutableListOf()

    var genresList: List<GenreNetwork>
        get() = genres
        set(genres) {
            this.genres = genres
        }
}

@Serializable
class ActorsResponse {
    @SerialName("id")
    val id: Int = 0

    @SerialName("cast")
    private var actors: List<ActorNetwork> = mutableListOf()

    var actorsList: List<ActorNetwork>
        get() = actors
        set(actorsList) {
            this.actors = actorsList
        }

    fun getActorsListModel(): List<ModelData.Actor> {
        var actorList = mutableListOf<ModelData.Actor>()
        actors.forEach {
            actorList.add(
                ModelData.Actor(
                    it.id,
                    it.name,
                    BaseURL.PATH_POSTER + it.profile_path
                )
            )
        }
        return actorList
    }
}

@Serializable
data class MovieNetwork(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("title")
    val title: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("poster_path")
    val poster: String = "",
    @SerialName("backdrop_path")
    val backdrop: String = "",
    @SerialName("vote_average")
    val ratings: Float = 0F,
    val numberOfRatings: Int = -1,
    @SerialName("genre_ids")
    val genre: List<Int>,
) {
    fun getMovieModelData(genreList: List<GenreNetwork>): ModelData.Movie {
        var ganreList = mutableListOf<ModelData.Genre>()
        genre.forEach { idGenre ->
            genreList.find { it.id == idGenre }
                ?.let { ganreList.add(ModelData.Genre(it.id, it.name)) }
        }
        return ModelData.Movie(
            id,
            title,
            overview,
            BaseURL.PATH_POSTER + poster,
            BaseURL.PATH_BACKDROP + backdrop,
            -1F,
            -1,
            -1,
            -1,
            false,
            ganreList
        )
    }
}

@Serializable
data class GenreNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

@Serializable
data class ActorNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profile_path: String?
)