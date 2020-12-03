package ru.eremite.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMovieDetails : Fragment() {
    private var listener: TopMainMenuClickListener? = null
    private var actor_list_recycler: RecyclerView? = null
    private var movie: Movie? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_movie_details, container, false)
        val id_movie:Int? = arguments?.getInt("id_movie")
        id_movie?.let { movie = MoviesDataSource().getMovies()[it] }
        val backButton:TextView = view.findViewById<TextView>(R.id.back)
        backButton.setOnClickListener{ listener?.onMoviesListActiv() }
        val poster = view.findViewById<ImageView>(R.id.poster_image_view)
        val age:TextView = view.findViewById<TextView>(R.id.age_text_view)
        val name:TextView = view.findViewById<TextView>(R.id.movie_name_text_view)
        val genre:TextView = view.findViewById<TextView>(R.id.movie_genre_text_view)
        val rating:RatingBar = view.findViewById<RatingBar>(R.id.movie_rating_bar)
        val posts:TextView = view.findViewById<TextView>(R.id.movie_posts_text_view)
        val store:TextView = view.findViewById<TextView>(R.id.movie_store_text_view)

        movie?.let{
            poster.setBackgroundResource(it.poster)
            age.setText(it.age)
            name.setText(it.name)
            genre.setText(it.genre)
            rating.rating = it.rating.toFloat()
            posts.setText(it.reviews)
            store.setText(it.store)
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actor_list_recycler = view.findViewById(R.id.actors_list_recycler_view)
        actor_list_recycler?.adapter = ActorsAdapter(clickListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopMainMenuClickListener){
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        actor_list_recycler = null
        super.onDetach()
    }
    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {
        (actor_list_recycler?.adapter as? ActorsAdapter)?.apply {
            bindActors(movie!!.actors)
        }
    }

    private fun doOnClick( movie: Movie):Movie {
        return movie;
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }


    companion object {
        fun newInstance(id_movie: Int): FragmentMovieDetails {
            val args = Bundle()
            args.putInt("id_movie", id_movie)
            val fragment = FragmentMovieDetails()
            fragment.arguments = args
            return fragment
        }
    }


}