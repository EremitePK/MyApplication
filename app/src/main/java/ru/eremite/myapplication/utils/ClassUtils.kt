package ru.eremite.myapplication.utils

import android.content.Context
import ru.eremite.myapplication.data.ModelData
import ru.eremite.myapplication.data.loadMovies

class ClassUtils {
    fun getURI(failName: String): String {
        return "http://lardis.ru/academ/webp/$failName.webp"
    }

    suspend fun loadData(context: Context): List<ModelData.Movie> {
        return loadMovies(context)
    }

    suspend fun loadMovie(context: Context, idMovie: Int): ModelData.Movie? {
        return loadMovies(context).find { it.id==idMovie }
    }
}