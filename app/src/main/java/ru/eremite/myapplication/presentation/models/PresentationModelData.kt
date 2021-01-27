package ru.eremite.myapplication.presentation.models

sealed class PresentationModelData(open val id: Int) {
    data class ActorPresentation(
        override val id: Int,
        val name: String,
        val picture: String
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is ActorPresentation -> id == other.id
                else -> false
            }
        }
    }

    data class GenrePresentation(
        override val id: Int,
        val name: String,
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is GenrePresentation -> id == other.id
                else -> false
            }
        }
    }

    data class MoviePresentation(
        override val id: Int = -1,
        val title: String = "",
        val overview: String = "",
        val poster: String? = "",
        val backdrop: String? = "",
        val ratings: Float = 0F,
        val numberOfRatings: Int = -1,
        var minimumAge: String = "",
        var runtime: Int = -1,
        var like: Boolean = false,
        val genres: List<GenrePresentation> = listOf()
    ) : PresentationModelData(id) {
        override fun equals(other: Any?): Boolean {
            return when (other) {
                is MoviePresentation -> ((id == other.id) && (like == other.like))
                else -> false
            }
        }

        fun getGeners(): String {
            var stringGenre = "";
            genres.forEach {
                if (stringGenre != "") {
                    stringGenre = "$stringGenre, "
                }
                stringGenre += it.name
            }
            return stringGenre
        }
    }
}