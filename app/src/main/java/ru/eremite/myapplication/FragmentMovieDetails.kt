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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies
import ru.eremite.myapplication.utils.ElementsRecyclerView
import ru.eremite.myapplication.utils.ParamViewHolder


class FragmentMovieDetails() : Fragment() {
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
        val idMovie: Int? = arguments?.getInt(MOVIE_ID_KEY)
        val backButton: TextView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            listener?.onMoviesListActiv()
        }
        val poster: ImageView = view.findViewById(R.id.poster_image_view)
        val age: TextView = view.findViewById(R.id.age_text_view)
        val name: TextView = view.findViewById(R.id.movie_name_text_view)
        val genre: TextView = view.findViewById(R.id.movie_genre_text_view)
        val rating: RatingBar = view.findViewById(R.id.movie_rating_bar)
        val posts: TextView = view.findViewById(R.id.movie_posts_text_view)
        val store: TextView = view.findViewById(R.id.movie_store_text_view)
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val listMovies = loadMovies(requireContext())
            idMovie?.let {
                movie = listMovies.find { it.id == idMovie }
            }
            movie?.let { it ->
                it.backdrop?.let {
                    Glide.with(context)
                        .load(it)
                        .into(poster)
                }
                age.text = it.minimumAge.toString()
                name.text = it.title
                genre.text = it.getGeners()
                rating.rating = it.ratings / 2
                posts.text = it.numberOfRatings.toString()
                store.text = it.overview
            }
            val l = listOf<ParamViewHolder>(
                ParamViewHolder(
                    2,
                    "ru.eremite.myapplication.MovieViewHolder",
                    R.layout.view_holder_movie
                ),
                ParamViewHolder(
                    3,
                    "ru.eremite.myapplication.ActorViewHolder",
                    R.layout.view_holder_actor
                )
            )
            actorsAdapter = RecyclerViewAdapter(
                null, null, movie?.actors,
                ElementsRecyclerView.MainCreatorViewHolder(l)
            )
            actorListRecycler?.adapter = actorsAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actorListRecycler = view.findViewById(R.id.actors_list_recycler_view)
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
        updateData()
    }

    private fun updateData() {
        movie?.let {
            actorsAdapter.bindRecyclerView(it.actors)
        }
    }

    companion object {
        private const val MOVIE_ID_KEY = "id_movie"
        fun newInstance(idMovie: Int) = FragmentMovieDetails().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID_KEY, idMovie)
            }
        }
    }
}