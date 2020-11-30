package ru.eremite.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), TopMainMenuClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            OnMoviesListActiv()
        }
    }

    override fun OnMoviesListActiv() {
        supportFragmentManager.beginTransaction()
                ?.replace(R.id.fragment_container_view, FragmentMoviesList())
                ?.commit()
    }

    override fun OnMovieDetailList() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, MovieDetailsActivity())
                .commit()
    }
}

interface TopMainMenuClickListener{
    fun OnMoviesListActiv()
    fun OnMovieDetailList()
}