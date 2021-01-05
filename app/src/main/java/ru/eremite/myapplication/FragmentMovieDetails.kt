package ru.eremite.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eremite.myapplication.ViewModel.MovieDetailsViewModel
import ru.eremite.myapplication.ViewModel.MovieDetailsViewModelFactory
import ru.eremite.myapplication.ViewModel.PresentationModelData

class FragmentMovieDetails : Fragment() {
    private var listener: TopMainMenuClickListener? = null
    private var idMovie: Int? = null
    private lateinit var actorsAdaptor: ActorsAdapter
    private var posterImageView: ImageView? = null
    private var ageTextView: TextView? = null
    private var nameTextView: TextView? = null
    private var genreTextView: TextView? = null
    private var ratingRatingBar: RatingBar? = null
    private var postsTextView: TextView? = null
    private var storeTextView: TextView? = null
    private var stateLoaderDetailsMovie: View? = null
    private var actorListRecycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        idMovie = requireNotNull(arguments?.getInt(MOVIE_ID_KEY))
        val backButton: TextView = view.findViewById(R.id.back)
        backButton.setOnClickListener {
            listener?.onMoviesListActiv()
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.onMoviesListActiv()
                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        with(
            ViewModelProvider(requireActivity(), MovieDetailsViewModelFactory(this.requireContext())).get(
                MovieDetailsViewModel::class.java
            )
        ) {
            loadingCurrentMovie.observe(
                viewLifecycleOwner,
                ::loadingMovieDetails
            )
            currentMovie.observe(viewLifecycleOwner, ::updateMovieDetails)
            currentActorsList.observe(viewLifecycleOwner, ::updateAdapterActors)
            actorsAdaptor = ActorsAdapter()
            actorListRecycler?.adapter = actorsAdaptor
            idMovie?.let { findMovie(it) }
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

    private fun initViews(view: View) {
        actorListRecycler = view.findViewById(R.id.actors_list_recycler_view)
        stateLoaderDetailsMovie = view.findViewById(R.id.state_loader_details_movie_progress_bar)
        posterImageView = view.findViewById(R.id.poster_image_view)
        ageTextView = view.findViewById(R.id.age_text_view)
        nameTextView = view.findViewById(R.id.movie_name_text_view)
        genreTextView = view.findViewById(R.id.movie_genre_text_view)
        ratingRatingBar = view.findViewById(R.id.movie_rating_bar)
        postsTextView = view.findViewById(R.id.movie_posts_text_view)
        storeTextView = view.findViewById(R.id.movie_store_text_view)
    }

    private fun loadingMovieDetails(loading: Boolean) {
        actorListRecycler?.isInvisible = loading
        stateLoaderDetailsMovie?.isVisible = loading
        posterImageView?.isInvisible = loading
        ageTextView?.isInvisible = loading
        nameTextView?.isInvisible = loading
        genreTextView?.isInvisible = loading
        ratingRatingBar?.isInvisible = loading
        postsTextView?.isInvisible = loading
        storeTextView?.isInvisible = loading
    }

    private fun updateMovieDetails(currentMovie: PresentationModelData.MoviePresentation) {
        if (currentMovie.id != -1) {
            currentMovie?.let {
                it.backdrop?.let {
                    Glide.with(context)
                        .load(it)
                        .into(posterImageView)
                }
                ageTextView?.text = it.minimumAge.toString()
                nameTextView?.text = it.title
                genreTextView?.text = it.getGeners()
                ratingRatingBar?.rating = it.ratings / 2
                postsTextView?.text = it.numberOfRatings.toString()
                storeTextView?.text = it.overview
            }
        }

    }

    private fun updateAdapterActors(listActors: List<PresentationModelData.ActorPresentation>) {
        (actorsAdaptor).bindActors(listActors)
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