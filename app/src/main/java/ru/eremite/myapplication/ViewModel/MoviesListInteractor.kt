package ru.eremite.myapplication.ViewModel

import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.MovieRepositoryNetwork

class MoviesListInteractor(
    private val repository: MovieRepositoryNetwork
) {
    suspend fun loadMovies(currentPage: Int): List<ModelData.Movie> =
        repository.loadMovies(currentPage)

    suspend fun loadMoviesAgeRuntime(listMovies: List<ModelData.Movie>): List<ModelData.Movie> =
        repository.loadMoviesAgeRuntime(listMovies)

    suspend fun findMoviesByName(name: String, currentPage: Int): List<ModelData.Movie> =
        repository.findMoviesByName(name, currentPage)
}