package ru.eremite.myapplication.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.ModelData

class MoviesViewModel(private val interactor: MovieInteractor) : ViewModel() {
    private val mutableMoviesList = MutableLiveData<List<ModelData.Movie>>(emptyList())
    private val mutableLoadingMoviesList = MutableLiveData<Boolean>(false)
    private val mutableCurrentMovie = MutableLiveData<ModelData.Movie>(ModelData.Movie())
    private val mutableLoadingCurrentMovie = MutableLiveData<Boolean>(true)
    private val mutableCurrentActorsList = MutableLiveData<List<ModelData.Actor>>(emptyList())
    private val mutableLoadingCurrentActorsList = MutableLiveData<Boolean>(true)

    val moviesList: LiveData<List<ModelData.Movie>> get() = mutableMoviesList
    val loadingMoviesList: LiveData<Boolean> get() = mutableLoadingMoviesList
    val currentMovie: LiveData<ModelData.Movie> get() = mutableCurrentMovie
    val loadingCurrentMovie: LiveData<Boolean> get() = mutableLoadingCurrentMovie
    val currentActorsList: LiveData<List<ModelData.Actor>> get() = mutableCurrentActorsList
    val loadingCurrentActorsList: LiveData<Boolean> get() = mutableLoadingCurrentActorsList

    init {
        loadMovies()
    }

    private fun loadMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mutableLoadingMoviesList.value = true
            mutableMoviesList.value = interactor.loadMovies()
            mutableLoadingMoviesList.value = false
        }
    }

    fun findMovie(idMovie: Int) {
        mutableLoadingCurrentMovie.value = true
        mutableCurrentMovie.value = mutableMoviesList.value?.let {
            interactor.findMovies(
                idMovie,
                it
            ) ?: ModelData.Movie()
        }
        mutableCurrentMovie.value?.let {
            mutableLoadingCurrentMovie.value = false
            loadActorsList(idMovie)
        }
    }

    private fun loadActorsList(idMovie: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            mutableLoadingCurrentActorsList.value = true
            mutableCurrentActorsList.value = mutableMoviesList.value?.let {
                interactor.loadActorsList(
                    idMovie,
                    it
                ) ?: mutableListOf()
            }
            mutableLoadingCurrentActorsList.value = false
        }
    }
}