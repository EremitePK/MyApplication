package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ClassUtils
import ru.eremite.myapplication.utils.MovieViewModel


class FragmentMovieDetails(private val movieViewModel: MovieViewModel) : Fragment() {
    private var listener: TopMainMenuClickListener? = null
    private var actorListRecycler: RecyclerView? = null
    private var movie: ModelData.Movie? = null
    private lateinit var actorsAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idMovie: Int? = arguments?.getInt(MOVIE_ID_KEY)
        val backButton: TextView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            listener?.onMoviesListActiv()
        }
        actorListRecycler = view.findViewById<RecyclerView>(R.id.actors_list_recycler_view)
        var listActors: List<ModelData> = listOf()
        movieViewModel.getListMovies().value?.let { listMovies ->
            idMovie?.let { idMovie ->
                movie = listMovies.find { it.id == idMovie }
                listActors = movie?.actors ?: listOf()
            }
        }
        actorsAdapter = RecyclerViewAdapter(
            null, null, listActors,
            ClassUtils().mainCreatorViewHolder
        )
        actorListRecycler?.adapter = actorsAdapter
        updateData(view)
        movieViewModel.getListMovies().observe(viewLifecycleOwner, Observer {
            it?.let { listMovies ->
                idMovie?.let { idMovie ->
                    movie = listMovies.find { it.id == idMovie }
                }
                updateData(view)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        actorListRecycler = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        updateData(null)
    }

    private fun updateData(view: View?) {
        movie?.let { currentMovie ->
            view?.let {
                val poster: ImageView = view.findViewById(R.id.poster_image_view)
                val age: TextView = view.findViewById(R.id.age_text_view)
                val name: TextView = view.findViewById(R.id.movie_name_text_view)
                val genre: TextView = view.findViewById(R.id.movie_genre_text_view)
                val rating: RatingBar = view.findViewById(R.id.movie_rating_bar)
                val posts: TextView = view.findViewById(R.id.movie_posts_text_view)
                val store: TextView = view.findViewById(R.id.movie_store_text_view)
                currentMovie.backdrop?.let {
                    Glide.with(context)
                        .load(it)
                        .into(poster)
                }
                age.text = currentMovie.minimumAge.toString()
                name.text = currentMovie.title
                genre.text = currentMovie.getGeners()
                rating.rating = currentMovie.ratings / 2
                posts.text = currentMovie.numberOfRatings.toString()
                store.text = currentMovie.overview
            }
            actorsAdapter.bindRecyclerView(currentMovie.actors as List<ModelData>)
        }
    }

    companion object {
        private const val MOVIE_ID_KEY = "id_movie"
        fun newInstance(movieViewModel: MovieViewModel, idMovie: Int) =
            FragmentMovieDetails(movieViewModel).apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_KEY, idMovie)
                }
            }
    }
}