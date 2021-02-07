package ru.eremite.myapplication.domain.models

data class Genre(override val id: Int, val name: String) : ModelData(id)
