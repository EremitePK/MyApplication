package ru.eremite.myapplication.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies


class MovieViewModel(private val context: Context) : ViewModel() {
    var movieList: MutableLiveData<List<ModelData.Movie>> = MutableLiveData()

    init {
        updateListMovie()
    }

    fun getListMovies() = movieList

    fun updateListMovie() {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            movieList.value = loadMovies(context)
        }
    }

    class Factory(private val context: Context) : NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel(context) as T
        }
    }

}