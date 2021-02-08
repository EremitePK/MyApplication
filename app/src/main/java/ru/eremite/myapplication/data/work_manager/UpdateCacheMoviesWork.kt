package ru.eremite.myapplication.data.work_manager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.eremite.myapplication.data.repositories.MovieRepository


class UpdateCacheMoviesWork(private val context: Context, params: WorkerParameters) : Worker(
    context,
    params
) {
    override fun doWork(): Result {
        val repository = MovieRepository(context)
        CoroutineScope(Dispatchers.IO).launch {
            repository.loadMovies(0).collect {  }
        }
        return Result.success()
    }

}