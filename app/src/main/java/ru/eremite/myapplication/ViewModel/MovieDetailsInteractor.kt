package ru.eremite.myapplication.ViewModel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.eremite.myapplication.db.MovieDB
import ru.eremite.myapplication.utils.MovieNetwork

class MovieDetailsInteractor(
    private val applicationContext: Context,
    private val repository: MovieNetwork
) {
    private val cashDB = MovieDB(applicationContext)

    fun findMovies(idMovie: Int): Flow<PresentationModelData.MoviePresentation> = flow {
        emit(cashDB.findMovie(idMovie))
        emit(repository.findMovie(idMovie))
    }

    fun loadActorsList(idMovie: Int): Flow<List<PresentationModelData.ActorPresentation>> = flow {
        emit(cashDB.getActors(idMovie))
        val actorsList = repository.getActors(idMovie)
        cashDB.updateActorDataInDB(idMovie, actorsList)
        emit(actorsList)
    }
}