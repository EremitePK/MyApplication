package ru.eremite.myapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ElementsRecyclerView

class ActorViewHolder(itemView: View) : RecyclerViewHolder(itemView) {

    private val photo: ImageView = itemView.findViewById(R.id.actor_photo_image_view)
    private val name: TextView = itemView.findViewById(R.id.actor_name_text_view)

    override fun onBind(adapter: RecyclerViewAdapter, itemModelData: ModelData) {
        val actor = itemModelData as ModelData.Actor
        actor.picture?.let {
            Glide.with(context)
                .load(it)
                .into(photo)
        }
        name.text = actor.name
    }

    override fun onBind(adapter: RecyclerViewAdapter, elementView: ElementsRecyclerView) {
        TODO("Not yet implemented")
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context