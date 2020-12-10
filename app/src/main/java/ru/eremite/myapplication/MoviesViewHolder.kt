package ru.eremite.myapplication

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ElementsRecyclerView

class MovieViewHolder(itemView: View) : RecyclerViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.poster_movie_image_view)
    private val age: TextView = itemView.findViewById(R.id.movie_age_text_view)
    private val like: ImageView = itemView.findViewById(R.id.movie_like_image_view)
    private val genre: TextView = itemView.findViewById(R.id.movie_genre_text_view)
    private val rating: RatingBar = itemView.findViewById(R.id.movie_rating_bar)
    private val reviews: TextView = itemView.findViewById(R.id.movie_posts_text_view)
    private val name: TextView = itemView.findViewById(R.id.movie_name_text_view)
    private val duration: TextView = itemView.findViewById(R.id.movie_duration_text_view)

    override fun onBind(adapter: RecyclerViewAdapter, itemModelData: ModelData) {
        val movie = itemModelData as ModelData.Movie
        movie.poster?.let {
            Glide.with(context)
                .load(it)
                .into(poster)
        }
        age.text = context.getString(R.string.age_format, movie.minimumAge)
        if (movie.like) {
            like.setImageResource(R.drawable.like_check)
        } else {
            like.setImageResource(R.drawable.like)
        }

        genre.text = movie.getGeners()
        rating.rating = movie.ratings/2
        name.text = movie.title
        reviews.text = ""
        duration.text = context.getString(R.string.duration_format, movie.runtime.toString())

        itemView.setOnClickListener {
            adapter.clickListener?.onClick(movie.id)
        }
        like.setOnClickListener {
            adapter.clickListener?.onClickLike(movie.id)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBind(adapter: RecyclerViewAdapter, elementView: ElementsRecyclerView) {

    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context