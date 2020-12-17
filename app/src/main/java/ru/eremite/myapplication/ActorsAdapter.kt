package ru.eremite.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.eremite.myapplication.data.ModelData

class ActorsAdapter() : RecyclerView.Adapter<ActorsViewHolder>() {
    private var actors = listOf<ModelData.Actor>()

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

    fun bindActors(newActors: List<ModelData.Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

abstract class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
private class ActorDataViewHolder(itemView: View) : ActorsViewHolder(itemView) {

    private val photo: ImageView = itemView.findViewById(R.id.actor_photo_image_view)
    private val name: TextView = itemView.findViewById(R.id.actor_name_text_view)

    fun onBind(actor: ModelData.Actor) {
        actor.picture?.let {
            Glide.with(context)
                .load(it)
                .apply(
                    RequestOptions()
                        .centerInside()
                )
                .into(photo)
        }
        name.text = actor.name
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context