package ru.eremite.myapplication.presentation.movie_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.eremite.myapplication.data.repositories.MovieRepository

class MovieDetailsViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            MovieDetailsInteractor(MovieRepository(applicationContext))
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}