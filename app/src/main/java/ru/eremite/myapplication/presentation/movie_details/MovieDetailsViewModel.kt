package ru.eremite.myapplication.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.eremite.myapplication.presentation.models.PresentationModelData

class MovieDetailsViewModel(private val interactor: MovieDetailsInteractor) : ViewModel() {
    private val mutableCurrentMovie =
        MutableLiveData(PresentationModelData.MoviePresentation())
    private val mutableLoadingCurrentMovie = MutableLiveData(true)
    private val mutableCurrentActorsList =
        MutableLiveData<List<PresentationModelData.ActorPresentation>>(emptyList())
    private val mutableLoadingCurrentActorsList = MutableLiveData(true)

    val currentMovie: LiveData<PresentationModelData.MoviePresentation> get() = mutableCurrentMovie
    val loadingCurrentMovie: LiveData<Boolean> get() = mutableLoadingCurrentMovie
    val currentActorsList: LiveData<List<PresentationModelData.ActorPresentation>> get() = mutableCurrentActorsList

    fun findMovie(idMovie: Int) {
        viewModelScope.launch {
            mutableLoadingCurrentMovie.value = true
            interactor.findMovies(idMovie).collect { findMovie ->
                findMovie.let {
                    mutableCurrentMovie.value = it
                }
                mutableCurrentMovie.value?.let {
                    mutableLoadingCurrentMovie.value = false
                    loadActorsList(idMovie)
                }
            }
        }
    }

    private fun loadActorsList(idMovie: Int) {
        viewModelScope.launch {
            mutableLoadingCurrentActorsList.value = true
            interactor.loadActorsList(idMovie).collect {
                mutableCurrentActorsList.value = it
                mutableLoadingCurrentActorsList.value = false
            }
        }
    }
}