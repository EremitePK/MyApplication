package ru.eremite.myapplication.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

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

    val networkApi: Api = retrofit.create()
}