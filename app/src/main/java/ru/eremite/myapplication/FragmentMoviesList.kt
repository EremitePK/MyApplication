package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.Header
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ClassUtils

const val SpanCount = "spanCount"

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private lateinit var movies: List<ModelData.Movie>
    private var spanCount: Int? = 2
    private lateinit var moviesAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spanCount = arguments?.getInt(SpanCount)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieListRecycler = view.findViewById<RecyclerView>(R.id.movies_list_recycler_view)
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            movies = ClassUtils().loadData(requireContext())
            moviesAdapter = RecyclerViewAdapter(
                clickListener,
                Header(getString(R.string.name_movies_list), R.drawable.combined_shape),
                movies
            )
            movieListRecycler?.adapter = moviesAdapter
        }
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

    override fun onStart() {
        super.onStart()
    }

    private fun updateData() {
        moviesAdapter?.let {
            it.bindRecyclerView(movies)
        }
    }

    private fun doOnClick(idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike(idMovie: Int) {
        var copyList = movies.toMutableList();
        copyList.find { it.id == idMovie }.let {
            var currentMovie = it
            currentMovie = it?.copy(like = !it.like)
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


