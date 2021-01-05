package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.data.Header
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.MoviesViewModel
import ru.eremite.myapplication.utils.MoviesViewModelFactory

class FragmentMoviesList : Fragment() {

    private val viewModel: MoviesViewModel by viewModels { MoviesViewModelFactory(requireContext()) }
    private var listener: TopMainMenuClickListener? = null
    private lateinit var movies: List<ModelData.Movie>
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
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateAdapter)
        viewModel.loadingMoviesList.observe(this.viewLifecycleOwner, this::setLoadingMovies)
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

    private fun updateAdapter(listMovies: List<ModelData.Movie>) {
        movies = listMovies
        if (moviesAdapter == null) {
            moviesAdapter = RecyclerViewAdapter(
                clickListener,
                Header(getString(R.string.name_movies_list), R.drawable.combined_shape),
                movies
            )
            movieListRecycler?.adapter = moviesAdapter
        }
        moviesAdapter?.bindRecyclerView(movies)
    }

    private fun setLoadingMovies(loadingMovies:Boolean){
        movieListRecycler?.isInvisible = loadingMovies
        stateLoaderMovies?.isVisible = loadingMovies
    }

    private fun doOnClick(idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike(idMovie: Int) {
        var copyList = movies.toMutableList();
        copyList.find { it.id == idMovie }.let {
            it?.copy(like = !it.like)
        }
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


