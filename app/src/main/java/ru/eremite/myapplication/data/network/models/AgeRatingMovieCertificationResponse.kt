package ru.eremite.myapplication.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AgeRatingMovieCertificationResponse {
    @SerialName("certification")
    var certification = ""
}