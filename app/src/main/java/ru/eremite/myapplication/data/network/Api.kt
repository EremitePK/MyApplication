package ru.eremite.myapplication.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.eremite.myapplication.data.network.models.*

interface Api {
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