package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.utils.Header
import ru.eremite.myapplication.utils.ModelData
import ru.eremite.myapplication.utils.MoviesDataSource

const val SpanCount = "spanCount"

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movieListRecycler: RecyclerView? = null
    private val movies:List<ModelData.Movie> = MoviesDataSource().getMovies()
    private var spanCount:Int? = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       spanCount = arguments?.getInt(SpanCount)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieListRecycler = view.findViewById(R.id.movies_list_recycler_view)
        movieListRecycler?.adapter = RecyclerViewAdapter(clickListener, Header(getString(R.string.name_movies_list),R.drawable.combined_shape),movies)//MoviesAdapter(clickListener)
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

    private fun doOnClick( idMovie: Int) {
        listener?.onMovieDetailList(idMovie)
    }

    private fun doOnClickLike( idMovie: Int) {
        var copyList = movies.toMutableList();
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
}


