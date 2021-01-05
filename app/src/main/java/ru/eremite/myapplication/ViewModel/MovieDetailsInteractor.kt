package ru.eremite.myapplication.ViewModel

import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.MovieRepositoryNetwork

class MovieDetailsInteractor(
    private val repository: MovieRepositoryNetwork
) {
    suspend fun findMovies(idMovie: Int): ModelData.Movie? = repository.findMovie(idMovie)

    suspend fun loadActorsList(
        idMovie: Int
    ): List<ModelData.Actor>? =
        repository.getActors(idMovie)
}