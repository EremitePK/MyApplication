package ru.eremite.myapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.data.Header
import ru.eremite.myapplication.data.ModelData

class RecyclerViewAdapter(
    private val clickListener: OnRecyclerItemClicked,
    private val header: Header,
    private var movies: List<ModelData.Movie>
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER
            1 -> VIEW_TYPE_MOVIE
            else -> VIEW_TYPE_ACTORS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER ->
                MainHeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_header, parent, false)
                )
            else -> MainDataViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_recycler_view, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        when (holder) {
            is MainDataViewHolder -> {
                when (position) {
                    2 -> holder.onBind(clickListener, movies, 1)
                    else -> holder.onBind(clickListener, movies, 0)
                }
            }
            is MainHeaderViewHolder -> holder.onBind(header)
        }
    }

    override fun getItemCount(): Int = 2

    fun bindRecyclerView(newMovies: List<ModelData.Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

abstract class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MainHeaderViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private val imageHeader: ImageView = itemView.findViewById(R.id.header_image_view)
    private val nameHeader: TextView = itemView.findViewById(R.id.header_name_text_view)

    fun onBind(header: Header) {
        imageHeader.setImageResource(header.image)
        nameHeader.text = header.name
    }
}

class MainDataViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private val listRecyclerView: RecyclerView = itemView.findViewById(R.id.item_recycler_view)

    fun onBind(
        clickListener: OnRecyclerItemClicked,
        movies: List<ModelData.Movie>,
        typeList: Int
    ) {
        val context = itemView.context
        val spanCount: Int =
            (context as Activity).resources.configuration.screenWidthDp / 200 //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        when (typeList) {
            0 -> {
                listRecyclerView.layoutManager =
                    GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
                listRecyclerView.adapter = MoviesAdapter(clickListener)
                (listRecyclerView.adapter as? MoviesAdapter)?.apply {
                    bindMovies(movies)
                }
            }
            else -> {
                listRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                listRecyclerView.adapter = ActorsAdapter()
                (listRecyclerView.adapter as? ActorsAdapter)?.apply {
                    //bindActors(MoviesDataSource().getActors())
                }
            }
        }
    }
}
