package ru.eremite.myapplication.data.work_manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


object UpdateCacheWorkCreate {
    fun createWork(context: Context) {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).setRequiresCharging(
                true
            ).build()
        val constrainedRequest =
            PeriodicWorkRequest.Builder(UpdateCacheMoviesWork::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(context).enqueue(constrainedRequest)
    }
}