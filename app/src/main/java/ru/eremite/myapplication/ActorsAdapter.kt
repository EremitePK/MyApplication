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
import ru.eremite.myapplication.utils.Actor
import ru.eremite.myapplication.utils.ClassUtils

class ActorsAdapter() : RecyclerView.Adapter<ActorsViewHolder>() {
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
        actor.photoRes?.let {
            Glide.with(context)
                .load(it)
                .into(photo)}
        actor.photoURL?.let {
            Glide.with(context)
                .load(ClassUtils().getURI(it))
                .into(photo)
        }
        name.text = actor.name
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context