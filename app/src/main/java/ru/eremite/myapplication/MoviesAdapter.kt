package ru.eremite.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.eremite.myapplication.data.ModelData


class MoviesAdapter(private val clickListener: OnRecyclerItemClicked) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    private var movies = listOf<ModelData.Movie>()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_MOVIE
            else -> VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> {
                holder.onBind(movies[position])
                holder.itemView.setOnClickListener {
                    clickListener.onClick(movies[position].id)
                }
                (holder as DataViewHolder).like.setOnClickListener {
                    clickListener.onClickLike(movies[position].id)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<ModelData.Movie>) {
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

    fun onBind(movie: ModelData.Movie) {
        movie.poster?.let {
            Glide.with(context)
                .load(it)
                .apply(
                    RequestOptions()
                        .centerInside()
                )
                .into(poster)
        }
        age.text = context.getString(R.string.age_format, movie.minimumAge)
        if (movie.like) {
            like.setImageResource(R.drawable.like_check)
        } else {
            like.setImageResource(R.drawable.like)
        }

        genre.text = movie.getGeners()
        rating.rating = movie.ratings / 2
        name.text = movie.title
        reviews.text = ""
        duration.text = context.getString(R.string.duration_format, movie.runtime.toString())
    }
}

const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_MOVIE = 1
const val VIEW_TYPE_ACTORS = 2

interface OnRecyclerItemClicked {
    fun onClick(idMovie: Int)
    fun onClickLike(idMovie: Int)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context