package ru.eremite.myapplication.utils

import android.content.Context
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

abstract class MovieRepository() {
    abstract suspend fun loadMovies(currentPage: Int): List<ModelData.Movie>
    abstract suspend fun loadMoviesAgeRuntime(listMovies: List<ModelData.Movie>): List<ModelData.Movie>
    abstract fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie?
    abstract suspend fun findMovie(idMovie: Int): ModelData.Movie?
    abstract suspend fun getActors(
        idMovie: Int
    ): List<ModelData.Actor>?
    abstract suspend fun findMoviesByName(name: String, currentPage: Int): List<ModelData.Movie>
}

class MovieRepositoryFileJSON(private val context: Context) : MovieRepository() {
    override suspend fun loadMovies(currentPage: Int): List<ModelData.Movie> = loadMovies(context)
    override suspend fun loadMoviesAgeRuntime(listMovies: List<ModelData.Movie>): List<ModelData.Movie> {
        TODO("Not yet implemented")
    }

    override fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie? =
        moviesList?.find { it.id == idMovie }

    override suspend fun findMovie(idMovie: Int): ModelData.Movie? {
        TODO("Not yet implemented")
    }

    override suspend fun getActors(
        idMovie: Int
    ): List<ModelData.Actor>? = loadMovies(0)?.find { it.id == idMovie }?.actors

    override suspend fun findMoviesByName(name: String, currentPage: Int): List<ModelData.Movie> {
        TODO("Not yet implemented")
    }
}