package ru.eremite.myapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.ViewModel.MoviesListViewModel
import ru.eremite.myapplication.ViewModel.PresentationModelData
import ru.eremite.myapplication.data.Header

class RecyclerViewAdapter(
    private val clickListener: OnRecyclerItemClicked,
    private val header: Header,
    private var movies: List<PresentationModelData.MoviePresentation>,
    private val listViewModel: MoviesListViewModel
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER
            1 -> VIEW_TYPE_FIND
            2 -> VIEW_TYPE_MOVIE
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
            VIEW_TYPE_FIND ->
                MainFindViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_find_movie, parent, false)
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
                    3 -> holder.onBind(clickListener, movies, 1, listViewModel)
                    else -> holder.onBind(clickListener, movies, 0, listViewModel)
                }
            }
            is MainHeaderViewHolder -> holder.onBind(header)
            is MainFindViewHolder -> holder.onBind(listViewModel)
        }
    }

    override fun getItemCount(): Int = 3

    fun bindRecyclerView(newMovies: List<PresentationModelData.MoviePresentation>) {
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

class MainFindViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private val nameMovieTextView: TextView = itemView.findViewById(R.id.find_movie_name_edit_text)
    private val findButton: Button = itemView.findViewById(R.id.find_movie_by_name_button)

    fun onBind(listViewModel: MoviesListViewModel) {
        nameMovieTextView.text = listViewModel.сurrentFindName.value
        findButton.setOnClickListener { listViewModel.findMoviesByName(nameMovieTextView.text.toString()) }
    }
}

class MainDataViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private val listRecyclerView: RecyclerView = itemView.findViewById(R.id.item_recycler_view)

    fun onBind(
        clickListener: OnRecyclerItemClicked,
        movies: List<PresentationModelData.MoviePresentation>,
        typeList: Int,
        listViewModel: MoviesListViewModel
    ) {
        val context = itemView.context
        val spanCount: Int =
            (context as Activity).resources.configuration.screenWidthDp / 180 //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        when (typeList) {
            0 -> {
                if (listRecyclerView.layoutManager == null) listRecyclerView.layoutManager =
                    GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
                if (listRecyclerView.adapter == null) listRecyclerView.adapter =
                    MoviesAdapter(clickListener, listViewModel)
                val positionRecyclerView =
                    (listRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                (listRecyclerView.adapter as? MoviesAdapter)?.apply {
                    bindMovies(movies)
                }
                val countItem = listRecyclerView.adapter?.itemCount ?: 0
                if (listViewModel.сurrentMovePosition.value != RecyclerView.NO_POSITION && (listViewModel.сurrentMovePosition.value
                        ?: 0) < countItem
                ) {
                    listRecyclerView.scrollToPosition(listViewModel.сurrentMovePosition.value ?: 0)
                } else {
                    if (positionRecyclerView != RecyclerView.NO_POSITION && positionRecyclerView < countItem)
                        listRecyclerView.scrollToPosition(positionRecyclerView)
                }
                val mScrollTouchListener: RecyclerView.OnItemTouchListener = object :
                    RecyclerView.OnItemTouchListener {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        val action = e.action
                        listViewModel.setCurrentMoviePosition((rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
                        //if ((((rv.parent.parent as RecyclerView).layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 1)&&
                        //    ((rv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() != (rv.adapter?.itemCount?: 0))   ){
                        when (action) {
                            MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(
                                true
                            )
                        }
                        //}
                        return false
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
                }
                listRecyclerView?.addOnItemTouchListener(mScrollTouchListener)

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
