package ru.eremite.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.eremite.myapplication.utils.MovieViewModel

class MainActivity : AppCompatActivity(), TopMainMenuClickListener {
    val movieViewModel by lazy<MovieViewModel> { MovieViewModel(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            onMoviesListActiv()
        }
    }

    override fun onMoviesListActiv() {
        supportFragmentManager.beginTransaction()
            ?.replace(R.id.fragment_container_view, FragmentMoviesList.newInstance(movieViewModel))
            ?.commit()
    }

    override fun onMovieDetailList(id_movie: Int) {

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                FragmentMovieDetails.newInstance(movieViewModel, id_movie)
            )
            .commit()
    }
}

interface TopMainMenuClickListener {
    fun onMoviesListActiv()
    fun onMovieDetailList(id_movie: Int)
}