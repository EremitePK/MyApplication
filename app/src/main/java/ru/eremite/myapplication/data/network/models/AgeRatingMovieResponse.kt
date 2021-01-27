package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.eremite.myapplication.data.network.BaseDataTMDb

@Serializable
class AgeRatingMovieResponse {
    @SerialName("id")
    var idMovie = 0

    @SerialName("results")
    private var results: List<AgeRatingMovieCountryResponse> = emptyList()

    fun getResults() =
        results.find { it.country == BaseDataTMDb.BASE_COUNTRY_CERTIFICATION }?.getResults()
            ?: results.find { it.country == BaseDataTMDb.BASE_COUNTRY_CERTIFICATION2 }?.getResults()
            ?: ""
}