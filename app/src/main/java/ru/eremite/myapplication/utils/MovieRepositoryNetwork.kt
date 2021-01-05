package ru.eremite.myapplication.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.eremite.myapplication.data.*

class MovieRepositoryNetwork : MovieRepository() {
    override suspend fun loadMovies(currentPage: Int): List<ModelData.Movie> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        val listMovie = BaseDataTMDb.networkApi.getListMovies(currentPage + 1).getResults(genres)
        return listMovie
    }

    override suspend fun loadMoviesAgeRuntime(listMovies: List<ModelData.Movie>): List<ModelData.Movie> {
        listMovies.forEach {
            it.minimumAge = BaseDataTMDb.networkApi.getAgeRating(it.id).getResults()
            it.runtime = BaseDataTMDb.networkApi.getMovie(it.id).getResults().runtime
        }
        return listMovies
    }

    override fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie? {
        TODO("Not yet implemented")
    }

    override suspend fun findMovie(idMovie: Int): ModelData.Movie? {
        return BaseDataTMDb.networkApi.getMovie(idMovie).getResults()
    }

    override suspend fun getActors(
        idMovie: Int
    ): List<ModelData.Actor> =
        BaseDataTMDb.networkApi.getActors(idMovie).getActorsListModel()

    override suspend fun findMoviesByName(name: String, currentPage: Int): List<ModelData.Movie> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        val listMovie = BaseDataTMDb.networkApi.getFindMoviesByName(name, currentPage + 1)
            .getResults(genres)
        return listMovie
    }
}

object BaseDataTMDb {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_LANGUAGE = "ru-RU"
    const val BASE_COUNTRY_CERTIFICATION = "RU"
    const val BASE_COUNTRY_CERTIFICATION2 = "US"
    const val BASE_API_KEY = "37582d35e5fa2f4cf35421c38cd70735"
    const val BASE_MOVIE_POPULAR = "movie/popular?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val BASE_MOVIE_DETAILS = "movie/{IDMOVIE}?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val BASE_MOVIE_FIND =
        "search/movie?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE&include_adult=false"
    const val BASE_GENRES = "genre/movie/list?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val BASE_ACTORS = "movie/{IDMOVIE}/credits?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val BASE_AGERATING = "movie/{IDMOVIE}/release_dates?api_key=$BASE_API_KEY"
    const val PATH_POSTER = "http://image.tmdb.org/t/p/w185"
    const val PATH_BACKDROP = "http://image.tmdb.org/t/p/w500/"

    interface Api {
        @GET(BASE_MOVIE_DETAILS)
        suspend fun getMovie(@Path("IDMOVIE") idMovie: Int): MovieDetailsResponse

        @GET(BASE_MOVIE_POPULAR)
        suspend fun getListMovies(@Query("page") currentPage: Int): PopularMoviesResponse

        @GET(BASE_GENRES)
        suspend fun getGenre(): GenresResponse

        @GET(BASE_ACTORS)
        suspend fun getActors(@Path("IDMOVIE") idMovie: Int): ActorsResponse

        @GET(BASE_AGERATING)
        suspend fun getAgeRating(@Path("IDMOVIE") idMovie: Int): AgeRatingMovieResponse

        @GET(BASE_MOVIE_FIND)
        suspend fun getFindMoviesByName(
            @Query("query") currentQuery: String,
            @Query("page") currentPage: Int
        ): PopularMoviesResponse
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BaseDataTMDb.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val networkApi: Api = retrofit.create()


}