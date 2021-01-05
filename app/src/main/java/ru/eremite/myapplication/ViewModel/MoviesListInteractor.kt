package ru.eremite.myapplication.ViewModel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.eremite.myapplication.db.MovieDB
import ru.eremite.myapplication.utils.MovieNetwork

class MoviesListInteractor(
    private val applicationContext: Context,
    private val repository: MovieNetwork
) {
    private val cashDB = MovieDB(applicationContext)

    suspend fun updateMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> =
        repository.loadMovies(currentPage)

    fun loadMovies(currentPage: Int): Flow<List<PresentationModelData.MoviePresentation>> = flow {
        emit(cashDB.loadMovies(0))
        var listMovies = repository.loadMovies(currentPage)
        if ((listMovies != null) && (listMovies.count() > 0)) {
            emit(listMovies)
            listMovies = loadMoviesAgeRuntime(listMovies)
            emit(listMovies)
            cashDB.updateDataInDB(listMovies)
        }
    }

    suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> =
        repository.loadMoviesAgeRuntime(listMovies)

    suspend fun findMoviesByName(
        name: String,
        currentPage: Int
    ): List<PresentationModelData.MoviePresentation> =
        repository.findMoviesByName(name, currentPage)
}
