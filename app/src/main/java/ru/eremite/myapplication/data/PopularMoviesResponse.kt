package ru.eremite.myapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.ModelData.Movie
import ru.eremite.myapplication.utils.BaseDataTMDb

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

@Serializable
class AgeRatingMovieResponse {
    @SerialName("id")
    var idMovie = 0

    @SerialName("results")
    private var results: List<AgeRatingMovieCountryResponse> = emptyList()

    fun getResults() =
        results.find { it.country == BaseDataTMDb.BASE_COUNTRY_CERTIFICATION }?.getResults()
            ?: results.find { it.country == BaseDataTMDb.BASE_COUNTRY_CERTIFICATION2 }?.getResults()
            ?: ""
}

@Serializable
class AgeRatingMovieCountryResponse {
    @SerialName("iso_3166_1")
    var country = ""

    @SerialName("release_dates")
    private var results: List<AgeRatingMovieCertificationResponse> = emptyList()

    fun getResults() = results[0]?.certification ?: ""
}

@Serializable
class AgeRatingMovieCertificationResponse {
    @SerialName("certification")
    var certification = ""
}

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
        var genreMovie = mutableListOf<ModelData.Genre>()
        genre.forEach { genreMovie.add(ModelData.Genre(it.id, it.name)) }
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

@Serializable
class GenresResponse {
    @SerialName("genres")
    private var genres: List<GenreNetwork> = mutableListOf()

    var genresList: List<GenreNetwork> = genres
}

@Serializable
class ActorsResponse {
    @SerialName("id")
    val id: Int = 0

    @SerialName("cast")
    private var actors: List<ActorNetwork> = mutableListOf()

    var actorsList: List<ActorNetwork> = actors

    fun getActorsListModel(): List<ModelData.Actor> {
        var actorList = mutableListOf<ModelData.Actor>()
        actors.forEach {
            actorList.add(
                ModelData.Actor(
                    it.id,
                    it.name,
                    BaseDataTMDb.PATH_POSTER + it.profile_path
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