package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AgeRatingMovieCountryResponse {
    @SerialName("iso_3166_1")
    var country = ""

    @SerialName("release_dates")
    private var results: List<AgeRatingMovieCertificationResponse> = emptyList()

    fun getResults() = results[0].certification
}