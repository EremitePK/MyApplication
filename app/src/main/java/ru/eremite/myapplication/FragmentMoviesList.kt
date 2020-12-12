package ru.eremite.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ClassUtils
import ru.eremite.myapplication.utils.ElementsRecyclerView
import ru.eremite.myapplication.utils.MovieViewModel
import ru.eremite.myapplication.utils.OnRecyclerItemClicked

class FragmentMoviesList(private val movieViewModel: MovieViewModel) : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movieListRecycler: RecyclerView? = null
    private lateinit var moviesAdapter: RecyclerViewAdapter
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val spanCount: Int =
            (context as Activity).resources.configuration.screenWidthDp / 180 //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        val classUtils = ClassUtils()
        var listMovie: List<ModelData> = listOf()
        movieViewModel.getListMovies().value?.let {
            listMovie = it
        }
        val rvl = ElementsRecyclerView.RecyclerViewList(
            clickListener, listMovie,
            GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false),
            classUtils.mainCreatorViewHolder
        )
        moviesAdapter = rvl.adaptorRecyclerView
        movieViewModel.getListMovies().observe(viewLifecycleOwner, Observer {
            it?.let { moviesAdapter.bindRecyclerView(it as List<ModelData>) }
        })
        val listElemLayout = listOf<ElementsRecyclerView>(
            ElementsRecyclerView.Header(
                getString(R.string.name_movies_list),
                R.drawable.combined_shape
            ),
            rvl
        )
        recyclerViewAdapter = RecyclerViewAdapter(
            clickListener,
            listElemLayout,
            null,
            classUtils.mainCreatorViewHolder
        )
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
        movieViewModel.getListMovies().value?.let {
            moviesAdapter.bindRecyclerView(it)
        }
    }

    private fun doOnClick(idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike(idMovie: Int) {
        var copyList = movieViewModel.getListMovies().value
        copyList?.filter { it.id == idMovie }.let {
            //it?.get(0).like = it[0].copy(like = !it[0].like)
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
        fun newInstance(movieViewModel: MovieViewModel) = FragmentMoviesList(movieViewModel)
    }
}


