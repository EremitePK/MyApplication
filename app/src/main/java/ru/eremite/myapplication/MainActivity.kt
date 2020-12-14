package ru.eremite.myapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ClassUtils
import ru.eremite.myapplication.utils.MovieViewModel

class MainActivity : AppCompatActivity(), TopMainMenuClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            MovieViewModel(this)
            onMoviesListActiv()
        }

    }

    override fun onMoviesListActiv() {

        supportFragmentManager.beginTransaction()
            ?.replace(R.id.fragment_container_view, FragmentMoviesList.newInstance())
            ?.commit()
    }

    override fun onMovieDetailList(id_movie: Int) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                FragmentMovieDetails.newInstance(id_movie)
            )
            .commit()
    }
}

interface TopMainMenuClickListener {
    fun onMoviesListActiv()
    fun onMovieDetailList(id_movie: Int)
}