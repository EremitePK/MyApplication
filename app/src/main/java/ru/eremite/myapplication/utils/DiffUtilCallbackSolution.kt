package ru.eremite.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import ru.eremite.myapplication.utils.ModelData.*

class DiffUtilCallbackSolution(
    private val oldList: List<ModelData>,
    private val newList: List<ModelData>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when (oldItem){
            is  Movie -> {
                oldItem.like == (newItem as Movie).like
            }
            is Actor -> true
            else -> true
        }

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}