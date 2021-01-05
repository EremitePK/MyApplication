package ru.eremite.myapplication.utils

import android.content.Context
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

abstract class MoviePersistent() {
    abstract suspend fun loadMovies(): List<ModelData.Movie>
    abstract fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie?
    abstract suspend fun getActors(
        idMovie: Int,
        moviesList: List<ModelData.Movie>
    ): List<ModelData.Actor>?
}

class MoviePersistentFileJSON(private val context: Context) : MoviePersistent() {
    override suspend fun loadMovies(): List<ModelData.Movie> = loadMovies(context)
    override fun findMovie(idMovie: Int, moviesList: List<ModelData.Movie>): ModelData.Movie? =
        moviesList?.find { it.id == idMovie }

    override suspend fun getActors(
        idMovie: Int,
        moviesList: List<ModelData.Movie>
    ): List<ModelData.Actor>? = moviesList?.find { it.id == idMovie }?.actors
}