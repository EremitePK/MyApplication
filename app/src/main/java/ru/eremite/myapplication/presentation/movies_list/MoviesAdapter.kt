package ru.eremite.myapplication.presentation.movies_list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.eremite.myapplication.R
import ru.eremite.myapplication.presentation.models.PresentationModelData
import ru.eremite.myapplication.utils.DiffUtilCallbackSolution


class MoviesAdapter(
    private val clickListener: OnRecyclerItemClicked,
    private val listViewModel: MoviesListViewModel
) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    private var movies = mutableListOf<PresentationModelData.MoviePresentation>()

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
                holder.like.setOnClickListener {
                    clickListener.onClickLike(movies[position].id)
                }
            }
        }
        if ((!(listViewModel.loadingMoviesList.value
                ?: false)) && (!(listViewModel.updateMoviesList.value
                ?: false)) && (position >= movies.count() - 10)
        ) {
            listViewModel.updateLoadMovies()
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<PresentationModelData.MoviePresentation>) {
        val diffCallback = DiffUtilCallbackSolution(movies, newMovies)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        newMovies.forEach{movies.add(it.copy())}
        diffResult.dispatchUpdatesTo(this)
    }
}

abstract class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class DataViewHolder(itemView: View) : MoviesViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.poster_movie_image_view)
    private val age: TextView = itemView.findViewById(R.id.movie_age_text_view)
    val like: ImageView = itemView.findViewById(R.id.movie_like_image_view)
    private val genre: TextView = itemView.findViewById(R.id.movie_genre_text_view)
    private val rating: RatingBar = itemView.findViewById(R.id.movie_rating_bar)
    private val name: TextView = itemView.findViewById(R.id.movie_name_text_view)
    private val duration: TextView = itemView.findViewById(R.id.movie_duration_text_view)

    fun onBind(movie: PresentationModelData.MoviePresentation) {
        movie.poster?.let {
            Glide.with(context)
                .load(it)
                .apply(
                    RequestOptions()
                        .centerInside()
                )
                .into(poster)
        }
        if (movie.minimumAge == "") {
            age.visibility = View.INVISIBLE
        } else {
            age.visibility = View.VISIBLE
        }
        age.text = movie.minimumAge
        if (movie.like) {
            like.setImageResource(R.drawable.like_check)
        } else {
            like.setImageResource(R.drawable.like)
        }

        genre.text = movie.getGeners()
        rating.rating = movie.ratings / 2
        name.text = movie.title
        if (movie.runtime == 0) {
            duration.visibility = View.INVISIBLE
        } else {
            duration.visibility = View.VISIBLE
        }
        duration.text = context.getString(R.string.duration_format, movie.runtime)
    }
}

const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_FIND = 1
const val VIEW_TYPE_MOVIE = 2
const val VIEW_TYPE_ACTORS = 3

interface OnRecyclerItemClicked {
    fun onClick(idMovie: Int)
    fun onClickLike(idMovie: Int)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context