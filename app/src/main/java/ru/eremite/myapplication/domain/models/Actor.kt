package ru.eremite.myapplication.domain.models

data class Actor(
    override val id: Int,
    val name: String,
    val picture: String
) : ModelData(id)
