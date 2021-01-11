package ru.eremite.myapplication

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), TopMainMenuClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            onMoviesListActiv()
        }
    }

    override fun onMoviesListActiv() {
        val spanCount: Int =
            resources.configuration.screenWidthDp / 180 //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.

        supportFragmentManager.beginTransaction()
            ?.replace(R.id.fragment_container_view, FragmentMoviesList.newInstance())
            ?.commit()
    }

    override fun onMovieDetailList(id_movie: Int) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, FragmentMovieDetails.newInstance(id_movie))
            .commit()
    }
}

interface TopMainMenuClickListener {
    fun onMoviesListActiv()
    fun onMovieDetailList(id_movie: Int)
}