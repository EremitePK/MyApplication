package ru.eremite.myapplication.presentation.movies_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.eremite.myapplication.data.repositories.MovieRepository
import ru.eremite.myapplication.presentation.models.PresentationModelData

class MoviesListInteractor(private val repository: MovieRepository) {

    suspend fun updateMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> =
        repository.updateMovies(currentPage)

    fun loadMovies(currentPage: Int): Flow<List<PresentationModelData.MoviePresentation>> = flow {
        repository.loadMovies(currentPage).collect { value -> emit(value) }
    }

    fun ControlCacheUpdate(): Flow<List<PresentationModelData.MoviePresentation>> = flow{
        repository.UpdateCache().collect { value-> emit(value) }
    }

    suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> =
        repository.loadMoviesAgeRuntime(listMovies)

    suspend fun findMoviesByName(
        name: String,
        currentPage: Int
    ): List<PresentationModelData.MoviePresentation> =
        repository.findMoviesByName(name, currentPage)
}
