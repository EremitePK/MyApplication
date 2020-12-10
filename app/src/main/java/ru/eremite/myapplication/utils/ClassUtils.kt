package ru.eremite.myapplication.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.*
import ru.eremite.myapplication.data.ModelData

class ClassUtils {
    fun getURI(failName: String): String {
        return "http://lardis.ru/academ/webp/$failName.webp"
    }
}

// универсальный построитель (создатель) динамических форм RecycleView и адаптор
interface UniversalBind {
    fun bindRecyclerView(newList: List<ModelData>)
} // интерфейс должен быть реализован у каждого адаптора
// который может быть добавлен в качества RecycleViewList включеного в отображение списка

interface UniversalTypeHolderView {
    fun getTypeView(): Int
} //интерфей должен быть реализован у каждого ViewHolder который будет выводится в основной RecycleView

//все ViewHolder должны быть унаследованы от class RecyclerViewHolder(itemView: View)
sealed class ElementsRecyclerView {
    data class MainCreatorViewHolder(val parent: ViewGroup, val typeCreateViewHolder: Int) {
        fun createViewHolder(): RecyclerViewHolder {
            return when (typeCreateViewHolder) {
                0 -> MainHeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_header, parent, false)
                )
                2 -> MovieViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_movie, parent, false)
                )
                3 -> ActorViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_actor, parent, false)
                )
                else -> MainDataViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_recycler_view, parent, false)
                )
            }
        }
    }

    data class RecyclerViewList(
        val clickListener: OnRecyclerItemClicked,
        val listData: List<ModelData>,
        val layoutManager: RecyclerView.LayoutManager
    ) : ElementsRecyclerView(), UniversalTypeHolderView {
        val adaptorRecyclerView = RecyclerViewAdapter(clickListener, null, listData)
        override fun getTypeView() = 1
    }

    data class Header(
        val name: String = "",
        val image: Int = 0
    ) : ElementsRecyclerView(), UniversalTypeHolderView {
        override fun getTypeView() = 0
    }
}
// универсальный пстроитель форм RecycleView

interface OnRecyclerItemClicked {
    fun onClick(idMovie: Int)
    fun onClickLike(idMovie: Int)
}