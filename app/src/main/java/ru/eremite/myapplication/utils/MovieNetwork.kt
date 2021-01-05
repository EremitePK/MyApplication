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
import ru.eremite.myapplication.ViewModel.PresentationModelData
import ru.eremite.myapplication.data.*

class MovieNetwork : MovieRepository() {
    override suspend fun loadMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        return BaseDataTMDb.networkApi.getListMovies(currentPage + 1).getResults(genres).map{PresentationModelData.MoviePresentation(it)}
    }

    override suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> {
        listMovies.forEach {
            it.minimumAge = BaseDataTMDb.networkApi.getAgeRating(it.id).getResults()
            it.runtime = BaseDataTMDb.networkApi.getMovie(it.id).getResults().runtime
        }
        return listMovies
    }

    override fun findMovie(idMovie: Int, moviesList: List<PresentationModelData.MoviePresentation>): PresentationModelData.MoviePresentation? {
        TODO("Not yet implemented")
    }

    override suspend fun findMovie(idMovie: Int): PresentationModelData.MoviePresentation {
        return BaseDataTMDb.networkApi.getMovie(idMovie).getResults()?.let { PresentationModelData.MoviePresentation(it) }
    }

    override suspend fun getActors(
        idMovie: Int
    ): List<PresentationModelData.ActorPresentation> =
        BaseDataTMDb.networkApi.getActors(idMovie).getActorsListModel().map{PresentationModelData.ActorPresentation(it)}

    override suspend fun findMoviesByName(name: String, currentPage: Int): List<PresentationModelData.MoviePresentation> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        return BaseDataTMDb.networkApi.getFindMoviesByName(name, currentPage + 1)
            .getResults(genres).map { PresentationModelData.MoviePresentation(it) }
    }
}

interface NetworkApi {
    @GET(BaseDataTMDb.BASE_MOVIE_DETAILS)
    suspend fun getMovie(@Path("IDMOVIE") idMovie: Int): MovieDetailsResponse

    @GET(BaseDataTMDb.BASE_MOVIE_POPULAR)
    suspend fun getListMovies(@Query("page") currentPage: Int): PopularMoviesResponse

    @GET(BaseDataTMDb.BASE_GENRES)
    suspend fun getGenre(): GenresResponse

    @GET(BaseDataTMDb.BASE_ACTORS)
    suspend fun getActors(@Path("IDMOVIE") idMovie: Int): ActorsResponse

    @GET(BaseDataTMDb.BASE_AGERATING)
    suspend fun getAgeRating(@Path("IDMOVIE") idMovie: Int): AgeRatingMovieResponse

    @GET(BaseDataTMDb.BASE_MOVIE_FIND)
    suspend fun getFindMoviesByName(
        @Query("query") currentQuery: String,
        @Query("page") currentPage: Int
    ): PopularMoviesResponse
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

    val networkApi: NetworkApi = retrofit.create()


}