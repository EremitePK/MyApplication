package ru.eremite.myapplication.data

sealed class ModelData(open val id: Int) {
    data class Genre(override val id: Int, val name: String) : ModelData(id)

    data class Actor(
        override val id: Int,
        val name: String,
        val picture: String
    ) : ModelData(id)

    data class Movie(
        override val id: Int = -1,
        val title: String = "",
        val overview: String = "",
        val poster: String = "",
        val backdrop: String = "",
        val ratings: Float = 0F,
        val numberOfRatings: Int=-1,
        val minimumAge: Int = -1,
        val runtime: Int=-1,
        val like: Boolean=false,
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
}

data class Header(
    val name: String = "",
    val image: Int = 0
)
