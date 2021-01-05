package ru.eremite.myapplication.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.eremite.myapplication.utils.MovieNetwork

class MovieDetailsViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            MovieDetailsInteractor(applicationContext, MovieNetwork())
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}