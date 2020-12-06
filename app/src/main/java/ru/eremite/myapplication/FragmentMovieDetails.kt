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
import ru.eremite.myapplication.utils.ClassUtils
import ru.eremite.myapplication.utils.Movie
import ru.eremite.myapplication.utils.MoviesDataSource

const val MovieIdKey = "id_movie"

class FragmentMovieDetails : Fragment() {
    private var listener: TopMainMenuClickListener? = null
    private var actorListRecycler: RecyclerView? = null
    private var movie: Movie? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_movie_details, container, false)
        val idMovie:Int? = arguments?.getInt(MovieIdKey)
        idMovie?.let {
            movie = MoviesDataSource().getMovies()[it]
        }
        val backButton:TextView = view.findViewById(R.id.back)
        backButton.setOnClickListener{
            listener?.onMoviesListActiv()
        }
        val poster:ImageView = view.findViewById(R.id.poster_image_view)
        val age:TextView = view.findViewById(R.id.age_text_view)
        val name:TextView = view.findViewById(R.id.movie_name_text_view)
        val genre:TextView = view.findViewById(R.id.movie_genre_text_view)
        val rating:RatingBar = view.findViewById(R.id.movie_rating_bar)
        val posts:TextView = view.findViewById(R.id.movie_posts_text_view)
        val store:TextView = view.findViewById(R.id.movie_store_text_view)

        movie?.let{ it ->
            it.posterDetailsRes?.let {
                Glide.with(context)
                    .load(it)
                    .into(poster)
            }
            it.posterDetailsURL?.let {
                Glide.with(context)
                    .load(ClassUtils().getURI(it))
                    .into(poster)
            }
            age.text = it.age
            name.text = it.name
            genre.text = it.genreString
            rating.rating = it.rating.toFloat()
            posts.text = it.reviews
            store.text = it.store
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actorListRecycler = view.findViewById(R.id.actors_list_recycler_view)
        actorListRecycler?.adapter = ActorsAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener){
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
        (actorListRecycler?.adapter as? ActorsAdapter)?.apply {
            bindActors(movie!!.actorsList)
        }
    }

    companion object {
        fun newInstance(IdMovie: Int): FragmentMovieDetails {
            val args = Bundle()
            args.putInt(MovieIdKey, IdMovie)
            val fragment = FragmentMovieDetails()
            fragment.arguments = args
            return fragment
        }
    }
}