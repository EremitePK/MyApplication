package ru.eremite.myapplication.domain.models

data class Movie(
    override val id: Int = -1,
    val title: String = "",
    val overview: String = "",
    val poster: String? = "",
    val backdrop: String? = "",
    val ratings: Float = 0F,
    val numberOfRatings: Int = -1,
    var minimumAge: String = "",
    var runtime: Int = -1,
    val like: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val actors: List<Actor> = emptyList()
) : ModelData(id) {
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

