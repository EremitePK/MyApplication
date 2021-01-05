package ru.eremite.myapplication.utils

import ru.eremite.myapplication.data.ModelData

class MovieInteractor(
    private val persistent: MoviePersistentNetwork
) {
    suspend fun loadMovies(): List<ModelData.Movie> = persistent.loadMovies()
    fun findMovies(idMovie: Int, listMovies: List<ModelData.Movie>): ModelData.Movie? =
        persistent.findMovie(idMovie, listMovies)

    suspend fun loadActorsList(
        idMovie: Int,
        listMovies: List<ModelData.Movie>
    ): List<ModelData.Actor>? =
        persistent.getActors(idMovie, listMovies)
}