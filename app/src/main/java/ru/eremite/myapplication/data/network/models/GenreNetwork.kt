package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)