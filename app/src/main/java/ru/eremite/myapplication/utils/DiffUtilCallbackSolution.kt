package ru.eremite.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import ru.eremite.myapplication.ViewModel.PresentationModelData

class DiffUtilCallbackSolution(
    private val oldList: List<PresentationModelData>,
    private val newList: List<PresentationModelData>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.equals(newItem)

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}