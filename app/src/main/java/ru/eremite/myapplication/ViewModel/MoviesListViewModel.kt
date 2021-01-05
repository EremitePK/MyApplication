package ru.eremite.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListViewModel(private val interactor: MoviesListInteractor) : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val mutableMoviesList =
        MutableLiveData<List<PresentationModelData.MoviePresentation>>(emptyList())
    private val mutableLoadingMoviesList = MutableLiveData(false)
    private val mutableCurrentPageMovies = MutableLiveData(-1)
    private val mutableCurrentFindName = MutableLiveData("")
    private val mutableUpdateMoviesList = MutableLiveData(false)
    private val mutableCurrentMovePosition = MutableLiveData(0)

    val moviesList: LiveData<List<PresentationModelData.MoviePresentation>> get() = mutableMoviesList
    val loadingMoviesList: LiveData<Boolean> get() = mutableLoadingMoviesList
    val updateMoviesList: LiveData<Boolean> get() = mutableUpdateMoviesList
    val сurrentFindName: LiveData<String> get() = mutableCurrentFindName
    val сurrentMovePosition: LiveData<Int> get() = mutableCurrentMovePosition

    init {
        loadMovies()
    }

    private fun loadMovies() {
        if (!(mutableLoadingMoviesList.value ?: false)) {
            mutableCurrentPageMovies.value = mutableCurrentPageMovies.value?.plus(1)
            mutableLoadingMoviesList.value = true
            viewModelScope.launch {
                interactor.loadMovies(mutableCurrentPageMovies.value ?: 1)
                    .collect { listPresintetionMovie ->
                        mutableMoviesList.value = listPresintetionMovie
                        mutableLoadingMoviesList.value = false
                    }
            }
        }
    }

    private fun UpdateMovies() {
        if (!(mutableLoadingMoviesList.value ?: false)) {
            mutableUpdateMoviesList.value = true
            mutableCurrentPageMovies.value = mutableCurrentPageMovies.value?.plus(1)
            viewModelScope.launch {
                val listPresintetionMovie =
                    interactor.updateMovies(mutableCurrentPageMovies.value ?: 1)
                val curList = mutableMoviesList.value?.toMutableList()
                curList?.addAll(listPresintetionMovie)
                mutableMoviesList.value = curList

                val listMovies = interactor.loadMoviesAgeRuntime(listPresintetionMovie)
                listMovies.forEach { lmAR ->
                    curList?.find { it.id == lmAR.id }?.let {
                        it.minimumAge = lmAR.minimumAge
                        it.runtime = lmAR.runtime
                    }
                }
                mutableUpdateMoviesList.value = false
            }
        }
    }

    fun findMoviesByName(name: String, notViewLoader: Boolean = false) {
        if (name != mutableCurrentFindName.value) {
            mutableCurrentPageMovies.value = -1
            mutableMoviesList.value = emptyList()
        }
        mutableCurrentFindName.value = name
        if (name == "") {
            loadMovies()
        } else {
            mutableUpdateMoviesList.value = true
            mutableCurrentPageMovies.value = mutableCurrentPageMovies.value?.plus(1)
            if (!notViewLoader) mutableLoadingMoviesList.value = true
            viewModelScope.launch {
                val listPresintetionMovie =
                    interactor.findMoviesByName(name, mutableCurrentPageMovies.value ?: 1)
                val curList = mutableMoviesList.value?.toMutableList()
                curList?.addAll(listPresintetionMovie)
                mutableMoviesList.value = curList
                val listMovies = interactor.loadMoviesAgeRuntime(listPresintetionMovie)
                listMovies.forEach { lmAR ->
                    curList?.find { it.id == lmAR.id }?.let {
                        it.minimumAge = lmAR.minimumAge
                        it.runtime = lmAR.runtime
                    }
                }
                mutableUpdateMoviesList.value = false
                if (!notViewLoader) mutableLoadingMoviesList.value = false
            }
        }
    }

    fun updateLoadMovies() {
        if (!(mutableUpdateMoviesList.value ?: false)) {
            if (mutableCurrentFindName.value ?: "" == "") {
                UpdateMovies()
            } else {
                findMoviesByName(mutableCurrentFindName.value ?: "", true)
            }
        }
    }

    fun setCurrentMoviePosition(currentPosition: Int) {
        mutableCurrentMovePosition.value = currentPosition
    }

    fun setLikeInMovie(idMovie: Int) {
        var listMovie = mutableMoviesList.value ?: emptyList()
        listMovie.find { it.id == idMovie }?.like =
            !((mutableMoviesList.value?.find { it.id == idMovie }?.like) ?: false)
        mutableMoviesList.value = listMovie
    }
}