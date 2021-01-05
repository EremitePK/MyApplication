package ru.eremite.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val interactor: MovieDetailsInteractor) : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val mutableCurrentMovie =
        MutableLiveData(PresentationModelData.MoviePresentation())
    private val mutableLoadingCurrentMovie = MutableLiveData(true)
    private val mutableCurrentActorsList =
        MutableLiveData<List<PresentationModelData.ActorPresentation>>(emptyList())
    private val mutableLoadingCurrentActorsList = MutableLiveData(true)

    val currentMovie: LiveData<PresentationModelData.MoviePresentation> get() = mutableCurrentMovie
    val loadingCurrentMovie: LiveData<Boolean> get() = mutableLoadingCurrentMovie
    val currentActorsList: LiveData<List<PresentationModelData.ActorPresentation>> get() = mutableCurrentActorsList
    val loadingCurrentActorsList: LiveData<Boolean> get() = mutableLoadingCurrentActorsList

    fun findMovie(idMovie: Int) {
        viewModelScope.launch {
            mutableLoadingCurrentMovie.value = true
            val findMovie = interactor.findMovies(idMovie)
            findMovie?.let {
                mutableCurrentMovie.value = PresentationModelData.MoviePresentation(
                    it.id,
                    it.title,
                    it.overview,
                    it.poster,
                    it.backdrop,
                    it.ratings,
                    it.numberOfRatings,
                    it.minimumAge,
                    it.runtime,
                    it.like,
                    it.getGeners()
                )
            }
            mutableCurrentMovie.value?.let {
                mutableLoadingCurrentMovie.value = false
                loadActorsList(idMovie)
            }
        }
    }

    private fun loadActorsList(idMovie: Int) {
        viewModelScope.launch {
            mutableLoadingCurrentActorsList.value = true
            val listActors = interactor.loadActorsList(idMovie) ?: mutableListOf()
            var listActorsPresint: MutableList<PresentationModelData.ActorPresentation> =
                mutableListOf()
            listActors.forEach {
                listActorsPresint.add(
                    PresentationModelData.ActorPresentation(
                        it.id,
                        it.name,
                        it.picture
                    )
                )
            }
            mutableCurrentActorsList.value = listActorsPresint
            mutableLoadingCurrentActorsList.value = false
        }
    }
}