package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList : Fragment() {

    private var listener: TopMainMenuClickListener? = null
    private var movie_list_recycler: RecyclerView? = null
    private val movies:List<Movie> = MoviesDataSource().getMovies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        //val movie_1_click : ImageView = view.findViewById<ImageView>(R.id.poster_movie_1_image_view)
        //movie_1_click.setOnClickListener { listener?.onMovieDetailList() };
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movie_list_recycler = view.findViewById(R.id.movies_list_recycler_view)
        movie_list_recycler?.adapter = MoviesAdapter(clickListener)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            /*val args = Bundle()
            args.putString("android", academy)*/
            val fragment = FragmentMoviesList()
            /*fragment.arguments = args*/
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
        movie_list_recycler = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {
        (movie_list_recycler?.adapter as? MoviesAdapter)?.apply {
            bindMovies(movies)
        }
    }

    private fun doOnClick( movie: Movie):Movie {
        listener?.onMovieDetailList(movies.indexOf(movie))
        return movie;
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }
}
class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
                Movie("Avengers:  End Game",true,"13+","Action, Adventure, Fantasy",2,"125 Reviews","97 min",
                        "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.",
                        R.drawable.poster_1, listOf(Actor("Robert Downey Jr.",R.drawable.movie1),
                                                    Actor("Chris Evans",R.drawable.movie2),
                                                    Actor("Mark Ruffalo",R.drawable.movie3),
                                                    Actor("Chris Hemsworth",R.drawable.movie4))),
                Movie("Tenet",false,"16+","Action, Sci-Fi, Thriller",3,"98 Reviews","97 min","",R.drawable.poster_2),
                Movie("Black Widow",false,"13+","Action, Adventure, Sci-Fi",4,"38 Reviews","102 min","",R.drawable.poster_3),
                Movie("Wonder Woman 1984",false,"13+","Action, Adventure, Fantasy",5,"74 Reviews","120 min","",R.drawable.poster_4)
        )
    }
}
