package ru.eremite.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.eremite.myapplication.R
import ru.eremite.myapplication.data.work_manager.UpdateCacheWorkCreate
import ru.eremite.myapplication.presentation.movie_details.MovieDetailsFragment
import ru.eremite.myapplication.presentation.movies_list.MoviesListFragment

class MainActivity : AppCompatActivity(), TopMainMenuClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            onMoviesListActiv()
        }
        UpdateCacheWorkCreate.createWork(applicationContext)
    }

    override fun onMoviesListActiv() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, MoviesListFragment.newInstance())
            .commit()
    }

    override fun onMovieDetailList(idMovie: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, MovieDetailsFragment.newInstance(idMovie))
            .commit()
    }
}

interface TopMainMenuClickListener {
    fun onMoviesListActiv()
    fun onMovieDetailList(idMovie: Int)
}