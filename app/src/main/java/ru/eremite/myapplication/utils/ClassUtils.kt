package ru.eremite.myapplication.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.eremite.myapplication.*
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

class ClassUtils() {
    private var listParamViewHolder = listOf<ParamViewHolder>(
        ParamViewHolder(
            2,
            "ru.eremite.myapplication.MovieViewHolder",
            R.layout.view_holder_movie
        ),
        ParamViewHolder(
            3,
            "ru.eremite.myapplication.ActorViewHolder",
            R.layout.view_holder_actor
        )
    )
    val mainCreatorViewHolder by lazy {
        ElementsRecyclerView.MainCreatorViewHolder(
            listParamViewHolder
        )
    }
    var listMovies: List<ModelData.Movie> = listOf()
    suspend fun loadMoviesUtils(context: Context): List<ModelData.Movie> {
        listMovies = loadMovies(context)
        return listMovies
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

data class ParamViewHolder(val typeInt: Int, val nameClass: String, val idLayoutViewHolder: Int)

//все ViewHolder должны быть унаследованы от class RecyclerViewHolder(itemView: View)
//надо реализовать чтобы создавался один экземпляр класса MainCreatorViewHolder в него передавать
//список возможных ParamViewHolder. Сейчас приходится каждый раз созздавать список при вызове ВАЖНО
sealed class ElementsRecyclerView {
    data class MainCreatorViewHolder(private val listParamViewHolder: List<ParamViewHolder> = listOf()) {
        fun createViewHolder(parent: ViewGroup, typeCreateViewHolder: Int): RecyclerViewHolder {
            return when (typeCreateViewHolder) {
                0 -> MainHeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_header, parent, false)
                )
                1 -> MainDataViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_recycler_view, parent, false)
                )
                else -> {
                    listParamViewHolder.find { it.typeInt == typeCreateViewHolder }?.let {
                        val c = Class.forName(it.nameClass).getConstructor(View::class.java)
                        return (c.newInstance(
                            LayoutInflater.from(parent.context)
                                .inflate(it.idLayoutViewHolder, parent, false)
                        )) as RecyclerViewHolder
                    }
                    return MainHeaderViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.view_holder_header, parent, false)
                    )
                }
            }
        }
    }

    data class RecyclerViewList(
        val clickListener: OnRecyclerItemClicked,
        val listData: List<ModelData>,
        val layoutManager: RecyclerView.LayoutManager,
        val mainCreatorViewHolder: MainCreatorViewHolder
    ) : ElementsRecyclerView(), UniversalTypeHolderView {
        val adaptorRecyclerView =
            RecyclerViewAdapter(clickListener, null, listData, mainCreatorViewHolder)

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