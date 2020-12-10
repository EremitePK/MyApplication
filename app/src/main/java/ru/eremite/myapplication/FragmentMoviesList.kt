package ru.eremite.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies
import ru.eremite.myapplication.utils.ElementsRecyclerView
import ru.eremite.myapplication.utils.ModelDataOld
import ru.eremite.myapplication.utils.MoviesDataSource
import ru.eremite.myapplication.utils.OnRecyclerItemClicked

class FragmentMoviesList(private var listMovies: List<ModelData.Movie> = listOf()) : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movieListRecycler: RecyclerView? = null
    private val movies: List<ModelDataOld.MovieOld> = MoviesDataSource().getMovies()
    private lateinit var moviesAdapter: RecyclerViewAdapter
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            listMovies = loadMovies(requireContext())
            updateData()
        }

        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spanCount: Int =
            (context as Activity).resources.configuration.screenWidthDp / 180 //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        val rvl = ElementsRecyclerView.RecyclerViewList(
            clickListener, listMovies,
            GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
        )
        moviesAdapter = rvl.adaptorRecyclerView
        val listElemLayout = listOf<ElementsRecyclerView>(
            ElementsRecyclerView.Header(
                getString(R.string.name_movies_list),
                R.drawable.combined_shape
            ),
            rvl
        )
        recyclerViewAdapter = RecyclerViewAdapter(clickListener, listElemLayout, null)
        movieListRecycler?.adapter = recyclerViewAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener) {
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
        listMovies?.let {
            moviesAdapter.bindRecyclerView(it)
        }
    }

    private fun doOnClick(idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike(idMovie: Int) {
        var copyList = movies.toMutableList()
        copyList[idMovie] = copyList[idMovie].copy(like = !copyList[idMovie].like)
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


