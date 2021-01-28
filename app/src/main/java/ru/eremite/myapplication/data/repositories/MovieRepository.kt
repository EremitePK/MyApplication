package ru.eremite.myapplication.data.repositories

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.eremite.myapplication.data.db.MovieDB
import ru.eremite.myapplication.data.network.MovieNetwork
import ru.eremite.myapplication.presentation.models.PresentationModelData

class MovieRepository(applicationContext: Context) {
    private val cashDB = MovieDB(applicationContext)
    private val movieNetwork = MovieNetwork()
    suspend fun loadMovies(currentPage: Int): Flow<List<PresentationModelData.MoviePresentation>> =
        flow {
            emit(cashDB.loadMovies())
            var listMovies = movieNetwork.loadMovies(currentPage)
            if (listMovies.count() > 0) {
                emit(listMovies)
                listMovies = loadMoviesAgeRuntime(listMovies)
                emit(listMovies)
                cashDB.updateDataInDB(listMovies)
            }
        }

    suspend fun updateMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> =
        movieNetwork.loadMovies(currentPage)

    suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> =
        movieNetwork.loadMoviesAgeRuntime(listMovies)

    suspend fun findMoviesByName(
        name: String,
        currentPage: Int
    ): List<PresentationModelData.MoviePresentation> =
        movieNetwork.findMoviesByName(name, currentPage)

    fun findMovies(idMovie: Int): Flow<PresentationModelData.MoviePresentation> = flow {
        emit(cashDB.findMovie(idMovie))
        emit(movieNetwork.findMovie(idMovie))
    }

    fun UpdateCache(): Flow<List<PresentationModelData.MoviePresentation>> = flow {
        cashDB.getCacheUnderUpdate().collect { value -> emit(value) }
    }
    fun loadActorsList(idMovie: Int): Flow<List<PresentationModelData.ActorPresentation>> = flow {
        emit(cashDB.getActors(idMovie))
        val actorsList = movieNetwork.getActors(idMovie)
        cashDB.updateActorDataInDB(idMovie, actorsList)
        emit(actorsList)
    }
}
