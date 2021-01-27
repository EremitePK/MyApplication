package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse {
    @SerialName("genres")
    private var genres: List<GenreNetwork> = mutableListOf()

    var genresList: List<GenreNetwork> = genres
}