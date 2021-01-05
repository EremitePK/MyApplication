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
import ru.eremite.myapplication.data.ActorsResponse
import ru.eremite.myapplication.data.GenresResponse
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.MoviesResponse

class MoviePersistentNetwork : MoviePersistent() {
    override suspend fun loadMovies(): List<ModelData.Movie> {
        val genres = RetrofitModule.genreApi.getGenre().genresList
        return RetrofitModule.moviesApi.getMovies().getResults(genres)
    }

    override fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie? =
        moviesList?.find { it.id == idMovie }

    override suspend fun getActors(
        idMovie: Int,
        moviesList: List<ModelData.Movie>
    ): List<ModelData.Actor> =
        RetrofitModule.actorsApi.getActors(idMovie).getActorsListModel()
}

private object RetrofitModule {
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BaseURL.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi: MovieApi = retrofit.create()
    val genreApi: GenreApi = retrofit.create()
    val actorsApi: ActorsAPI = retrofit.create()
}

private interface MovieApi {
    @GET(BaseURL.BASE_MOVE_POPULAR)
    suspend fun getMovies(): MoviesResponse
}

private interface GenreApi {
    @GET(BaseURL.BASE_GENRES)
    suspend fun getGenre(): GenresResponse
}

private interface ActorsAPI {
    @GET(BaseURL.BASE_ACTORS)
    suspend fun getActors(@Path("IDMOVIE") IDMOVIE: Int): ActorsResponse
}

object BaseURL {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_LANGUAGE = "ru-RU"
    const val BASE_API_KEY = "37582d35e5fa2f4cf35421c38cd70735"
    const val BASE_MOVE_POPULAR =
        "movie/popular?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE&page=1"
    const val BASE_GENRES = "genre/movie/list?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val BASE_ACTORS = "movie/{IDMOVIE}/credits?api_key=$BASE_API_KEY&language=$BASE_LANGUAGE"
    const val PATH_POSTER = "http://image.tmdb.org/t/p/w185"
    const val PATH_BACKDROP = "http://image.tmdb.org/t/p/w500/"
}