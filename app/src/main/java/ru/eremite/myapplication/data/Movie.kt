package ru.eremite.myapplication.data

sealed class ModelData() {
    data class Genre(val id: Int, val name: String) : ModelData()

    data class Actor(
        val id: Int,
        val name: String,
        val picture: String
    ) : ModelData()

    data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val poster: String,
        val backdrop: String,
        val ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int,
        val like: Boolean,
        val genres: List<Genre>,
        val actors: List<Actor>
    ) : ModelData() {
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
