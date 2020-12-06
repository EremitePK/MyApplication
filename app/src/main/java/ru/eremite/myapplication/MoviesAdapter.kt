package ru.eremite.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import ru.eremite.myapplication.utils.ClassUtils
import ru.eremite.myapplication.utils.Movie


class MoviesAdapter(private val clickListener: OnRecyclerItemClicked) : RecyclerView.Adapter<MoviesViewHolder>() {
    private var movies = listOf<Movie>()

    override fun getItemViewType(position: Int): Int {
        return movies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        (holder as DataViewHolder).onBind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(movies[position])
        }
        holder.like.setOnClickListener {
            clickListener.onClickLike(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

abstract class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class DataViewHolder(itemView: View) : MoviesViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.poster_movie_image_view)
    private val age: TextView = itemView.findViewById(R.id.movie_age_text_view)
    val like: ImageView = itemView.findViewById(R.id.movie_like_image_view)
    private val genre: TextView = itemView.findViewById(R.id.movie_genre_text_view)
    private val rating: RatingBar = itemView.findViewById(R.id.movie_rating_bar)
    private val reviews: TextView = itemView.findViewById(R.id.movie_posts_text_view)
    private val name: TextView = itemView.findViewById(R.id.movie_name_text_view)
    private val duration: TextView = itemView.findViewById(R.id.movie_duration_text_view)

    fun onBind(movie: Movie) {
        movie.posterListRes?.let {
            Glide.with(context)
                .load(it)
                .into(poster)}
        movie.posterListURL?.let {
            Glide.with(context)
                .load(ClassUtils().getURI(it))
                .into(poster)
        }
        age.text = movie.age
        if (movie.like){
            like.setImageResource(R.drawable.like_check)
        } else {
            like.setImageResource(R.drawable.like)
        }

        genre.text = movie.genreString
        rating.rating = movie.rating.toFloat()
        name.text = movie.name
        reviews.text = movie.reviews+" Reviews"
        duration.text = movie.duration+" min"
    }
}

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
    fun onClickLike(movie: Movie)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context