package ru.eremite.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter(private val clickListener: OnRecyclerItemClicked) : RecyclerView.Adapter<ActorsViewHolder>() {
    private var actors = listOf<Actor>()

    override fun getItemViewType(position: Int): Int {
        return actors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorDataViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        (holder as ActorDataViewHolder).onBind(actors[position])
        holder.itemView.setOnClickListener {
            /*clickListener.onClick(actors[position])*/}

    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

abstract class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
private class ActorDataViewHolder(itemView: View) : ActorsViewHolder(itemView) {

    private val photo: ImageView = itemView.findViewById(R.id.actor_photo_image_view)
    private val name: TextView = itemView.findViewById(R.id.actor_name_text_view)

    fun onBind(actor: Actor) {
        photo.setBackgroundResource(actor.photo)
        name.text = actor.name
    }

    companion object {
        /*private val imageOption = RequestOptions()
                .placeholder(R.drawable.poster_1)
                .fallback(R.drawable.poster_1)
                .circleCrop()*/
    }
}

interface ActorOnRecyclerItemClicked {
    fun onClick(actor: Actor)
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

data class Actor(
        val name: String,
        val photo: Int
)