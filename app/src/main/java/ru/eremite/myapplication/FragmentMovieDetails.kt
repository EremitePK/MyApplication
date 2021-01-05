package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.MoviesViewModel
import ru.eremite.myapplication.utils.MoviesViewModelFactory

class FragmentMovieDetails : Fragment() {
    private val viewModel: MoviesViewModel by viewModels { MoviesViewModelFactory(requireContext()) }
    private var listener: TopMainMenuClickListener? = null
    private var movie: ModelData.Movie? = null
    private var idMovie: Int? = null
    private lateinit var actorsAdaptor: ActorsAdapter
    private var poster: ImageView? = null
    private var age: TextView? = null
    private var name: TextView? = null
    private var genre: TextView? = null
    private var rating: RatingBar? = null
    private var posts: TextView? = null
    private var store: TextView? = null
    private var stateLoaderDetailsMovie: View? = null
    private var actorListRecycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        idMovie = requireNotNull(arguments?.getInt(MOVIE_ID_KEY))
        val backButton: TextView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            listener?.onMoviesListActiv()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        viewModel.loadingCurrentMovie.observe(this.viewLifecycleOwner, this::loadingMovieDetails)
        viewModel.currentMovie.observe(this.viewLifecycleOwner, this::updateMovieDetails)
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateListMovies)
        viewModel.currentActorsList.observe(this.viewLifecycleOwner, this::updateAdapterActors)
        actorsAdaptor = ActorsAdapter()
        actorListRecycler?.adapter = actorsAdaptor
        idMovie?.let { viewModel.findMovie(it) }
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
        //updateData()
    }

    private fun initViews(view: View) {
        actorListRecycler = view.findViewById(R.id.actors_list_recycler_view)
        stateLoaderDetailsMovie = view.findViewById(R.id.state_loader_details_movie_progress_bar)
        poster = view.findViewById(R.id.poster_image_view)
        age = view.findViewById(R.id.age_text_view)
        name = view.findViewById(R.id.movie_name_text_view)
        genre = view.findViewById(R.id.movie_genre_text_view)
        rating = view.findViewById(R.id.movie_rating_bar)
        posts = view.findViewById(R.id.movie_posts_text_view)
        store = view.findViewById(R.id.movie_store_text_view)
    }

    private fun loadingMovieDetails(loading: Boolean) {
        actorListRecycler?.isInvisible = loading
        stateLoaderDetailsMovie?.isVisible = loading
        poster?.isInvisible = loading
        age?.isInvisible = loading
        name?.isInvisible = loading
        genre?.isInvisible = loading
        rating?.isInvisible = loading
        posts?.isInvisible = loading
        store?.isInvisible = loading
    }

    private fun updateMovieDetails(currentMovie: ModelData.Movie) {
        if (currentMovie.id != -1) {
            movie = currentMovie
            movie?.let {
                it.backdrop?.let {
                    Glide.with(context)
                        .load(it)
                        .into(poster)
                }
                age?.text = it.minimumAge.toString()
                name?.text = it.title
                genre?.text = it.getGeners()
                rating?.rating = it.ratings / 2
                posts?.text = it.numberOfRatings.toString()
                store?.text = it.overview
                updateData()
            }
        }

    }

    private fun updateListMovies(listMovies: List<ModelData.Movie>) {
        idMovie?.let { viewModel.findMovie(it) }
    }

    private fun updateAdapterActors(listActors: List<ModelData.Actor>) {
        (actorsAdaptor).bindActors(listActors)
    }

    private fun updateData() {
        movie?.let {
            (actorsAdaptor).bindActors(it.actors)
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