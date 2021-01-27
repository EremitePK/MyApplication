package ru.eremite.myapplication.data.network

import ru.eremite.myapplication.presentation.models.PresentationModelData
import ru.eremite.myapplication.utils.ModelsMapper

class MovieNetwork {
    suspend fun loadMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        return BaseDataTMDb.networkApi.getListMovies(currentPage + 1).getResults(genres)
            .map { ModelsMapper.toMoviePresentation(it) }
    }

    suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> {
        listMovies.forEach {
            it.minimumAge = BaseDataTMDb.networkApi.getAgeRating(it.id).getResults()
            it.runtime = BaseDataTMDb.networkApi.getMovie(it.id).getResults().runtime
        }
        return listMovies
    }

    suspend fun findMovie(idMovie: Int): PresentationModelData.MoviePresentation {
        return BaseDataTMDb.networkApi.getMovie(idMovie).getResults()
            .let { ModelsMapper.toMoviePresentation(it) }
    }

    suspend fun getActors(
        idMovie: Int
    ): List<PresentationModelData.ActorPresentation> =
        BaseDataTMDb.networkApi.getActors(idMovie).getActorsListModel()
            .map { ModelsMapper.toActorPresentation(it) }

    suspend fun findMoviesByName(
        name: String,
        currentPage: Int
    ): List<PresentationModelData.MoviePresentation> {
        val genres = BaseDataTMDb.networkApi.getGenre().genresList
        return BaseDataTMDb.networkApi.getFindMoviesByName(name, currentPage + 1)
            .getResults(genres).map { ModelsMapper.toMoviePresentation(it) }
    }
}