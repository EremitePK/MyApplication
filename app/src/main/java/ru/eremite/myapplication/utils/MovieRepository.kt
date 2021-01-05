package ru.eremite.myapplication.utils

import android.content.Context
import ru.eremite.myapplication.ViewModel.PresentationModelData
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

abstract class MovieRepository() {
    abstract suspend fun loadMovies(currentPage: Int): List<PresentationModelData.MoviePresentation>
    abstract suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation>
    abstract fun findMovie(idMovie: Int, moviesList: List<PresentationModelData.MoviePresentation>) : PresentationModelData.MoviePresentation?
    abstract suspend fun findMovie(idMovie: Int): PresentationModelData.MoviePresentation?
    abstract suspend fun getActors(
        idMovie: Int
    ): List<PresentationModelData.ActorPresentation>?
    abstract suspend fun findMoviesByName(name: String, currentPage: Int): List<PresentationModelData.MoviePresentation>
}
