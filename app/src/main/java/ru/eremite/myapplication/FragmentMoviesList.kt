package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.utils.Movie
import ru.eremite.myapplication.utils.MoviesDataSource

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movieListRecycler: RecyclerView? = null
    private val movies:MutableList<Movie> = MoviesDataSource().getMovies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        movieListRecycler?.adapter = MoviesAdapter(clickListener)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            val fragment = FragmentMoviesList()
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener){
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        movieListRecycler = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (movieListRecycler?.adapter as? MoviesAdapter)?.apply {
            bindMovies(movies)
        }
    }

    private fun doOnClick( movie: Movie) {
        listener?.onMovieDetailList(movies.indexOf(movie))
    }

    private fun doOnClickLike( movie: Movie) {
        movies[movies.indexOf(movie)] = movie.copy(like = !movie.like)
        updateData()
    }
    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
        override fun onClickLike(movie: Movie) {
            doOnClickLike(movie)
        }
    }
}


