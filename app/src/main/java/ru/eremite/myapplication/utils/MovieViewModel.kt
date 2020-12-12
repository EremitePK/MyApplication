package ru.eremite.myapplication.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

class MovieViewModel(private val context: Context) : ViewModel() {
    var movieList: MutableLiveData<List<ModelData.Movie>> = MutableLiveData()

    init {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            movieList.value = loadMovies(context)
        }
    }

    fun getListMovies() = movieList

    fun updateListMovie() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            movieList.value = loadMovies(context)
        }
    }
}