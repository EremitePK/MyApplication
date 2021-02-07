package ru.eremite.myapplication.presentation.movie_details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.eremite.myapplication.data.repositories.MovieRepository
import ru.eremite.myapplication.presentation.models.PresentationModelData

class MovieDetailsInteractor(private val repository: MovieRepository) {

    fun findMovies(idMovie: Int): Flow<PresentationModelData.MoviePresentation> = flow {
        repository.findMovies(idMovie).collect { value -> emit(value) }
    }

    fun loadActorsList(idMovie: Int): Flow<List<PresentationModelData.ActorPresentation>> = flow {
        repository.loadActorsList(idMovie).collect { value -> emit(value) }

    }
}