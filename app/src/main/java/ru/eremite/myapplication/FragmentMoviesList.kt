package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.ViewModel.MoviesListViewModel
import ru.eremite.myapplication.ViewModel.MoviesListViewModelFactory
import ru.eremite.myapplication.ViewModel.PresentationModelData
import ru.eremite.myapplication.data.Header


class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movies: List<PresentationModelData.MoviePresentation> = mutableListOf()
    private var moviesAdapter: RecyclerViewAdapter? = null
    private var movieListRecycler: RecyclerView? = null
    private var stateLoaderMovies: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        val listViewModel = ViewModelProvider(requireActivity(), MoviesListViewModelFactory()).get(
            MoviesListViewModel::class.java
        )
        listViewModel.moviesList.observe(this.viewLifecycleOwner, this::updateAdapter)
        listViewModel.loadingMoviesList.observe(this.viewLifecycleOwner, this::setLoadingMovies)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    private fun initViews(view: View) {
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        stateLoaderMovies = view.findViewById(R.id.state_loader_movies_progress_bar)
    }

    private fun updateAdapter(listMovies: List<PresentationModelData.MoviePresentation>) {
        movies = listMovies
        if (moviesAdapter == null) {
            val listViewModel =
                ViewModelProvider(requireActivity(), MoviesListViewModelFactory()).get(
                    MoviesListViewModel::class.java
                )
            moviesAdapter = RecyclerViewAdapter(
                clickListener,
                Header(getString(R.string.name_movies_list), R.drawable.combined_shape),
                movies,
                listViewModel
            )
            movieListRecycler?.adapter = moviesAdapter
        }
        moviesAdapter?.bindRecyclerView(movies)
    }

    private fun setLoadingMovies(loadingMovies: Boolean) {
        movieListRecycler?.isInvisible = loadingMovies
        stateLoaderMovies?.isVisible = loadingMovies
    }

    private fun doOnClick(idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike(idMovie: Int) {
        val listViewModel = ViewModelProvider(requireActivity(), MoviesListViewModelFactory()).get(
            MoviesListViewModel::class.java
        )
        listViewModel.setLikeInMovie(idMovie)
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(idMovie: Int) {
            doOnClick(idMovie)
        }

        override fun onClickLike(idMovie: Int) {
            doOnClickLike(idMovie)
        }
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}


