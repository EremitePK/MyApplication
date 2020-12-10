package ru.eremite.myapplication

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.utils.ElementsRecyclerView
import ru.eremite.myapplication.utils.OnRecyclerItemClicked
import ru.eremite.myapplication.utils.UniversalBind
import ru.eremite.myapplication.utils.UniversalTypeHolderView

class RecyclerViewAdapter(
    val clickListener: OnRecyclerItemClicked? = null,
    private var listElements: List<ElementsRecyclerView>? = null,
    private var listData: List<ModelData>? = null
) : RecyclerView.Adapter<RecyclerViewHolder>(), UniversalBind {
    override fun getItemViewType(position: Int): Int {
        listElements?.let { return (it[position] as UniversalTypeHolderView).getTypeView() }
        listData?.let { return (it[position] as UniversalTypeHolderView).getTypeView() }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return ElementsRecyclerView.MainCreatorViewHolder(parent, viewType).createViewHolder()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        listElements?.get(position)?.let { holder.onBind(this, it) }
        listData?.get(position)?.let { holder.onBind(this, it) }
    }

    override fun getItemCount(): Int {
        listElements?.let { return it.size }
        listData?.let { return it.size }
        return 0
    }

    override fun bindRecyclerView(newList: List<ModelData>) {
        listData = newList
        notifyDataSetChanged()
    }
}

abstract class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(adapter: RecyclerViewAdapter, elementView: ElementsRecyclerView)
    abstract fun onBind(adapter: RecyclerViewAdapter, itemModelData: ModelData)
}

class MainHeaderViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private val imageHeader: ImageView = itemView.findViewById(R.id.header_image_view)
    private val nameHeader: TextView = itemView.findViewById(R.id.header_name_text_view)

    override fun onBind(adapter: RecyclerViewAdapter, elementView: ElementsRecyclerView) {
        (elementView as ElementsRecyclerView.Header).apply {
            imageHeader.setImageResource(this.image)
            nameHeader.text = this.name
        }
    }

    override fun onBind(adapter: RecyclerViewAdapter, itemModelData: ModelData) {
        TODO("Not yet implemented")
    }
}

class MainDataViewHolder(itemView: View) : RecyclerViewHolder(itemView) {
    private var listRecyclerView: RecyclerView = itemView.findViewById(R.id.item_recycler_view)

    override fun onBind(adapter: RecyclerViewAdapter, elementView: ElementsRecyclerView) {
        (elementView as ElementsRecyclerView.RecyclerViewList).apply {
            listRecyclerView?.layoutManager = this.layoutManager
            listRecyclerView?.adapter = this.adaptorRecyclerView
            (listRecyclerView?.adapter as UniversalBind).bindRecyclerView(this.listData)
        }
    }

    override fun onBind(adapter: RecyclerViewAdapter, itemModelData: ModelData) {
        TODO("Not yet implemented")
    }
}
