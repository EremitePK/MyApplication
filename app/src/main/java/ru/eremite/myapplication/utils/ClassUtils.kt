package ru.eremite.myapplication.utils

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics

class ClassUtils {
    fun getURI(failName: String):String{
        return "http://lardis.ru/academ/webp/$failName.webp"
    }
}