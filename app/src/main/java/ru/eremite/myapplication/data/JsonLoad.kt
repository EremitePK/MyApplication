package ru.eremite.myapplication.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre (val id: Int, val name: String)

@Serializable
private class JsonActor(
        val id: Int,
        val name: String,
        @SerialName("profile_path")
        val profilePicture: String
)

@Serializable
private class JsonMovie(
        val id: Int,
        val title: String,
        @SerialName("poster_path")
        val posterPicture: String,
        @SerialName("backdrop_path")
        val backdropPicture: String,
        val runtime: Int,
        @SerialName("genre_ids")
        val genreIds: List<Int>,
        val actors: List<Int>,
        @SerialName("vote_average")
        val ratings: Float,
        @SerialName("vote_count")
        val votesCount: Int,
        val overview: String,
        val adult: Boolean
)

private suspend fun loadGenres(context: Context): List<ModelData.Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<ModelData.Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { ModelData.Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<ModelData.Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<ModelData.Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map {
        ModelData.Actor(
            id = it.id,
            name = it.name,
            picture = it.profilePicture
        )
    }
}

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<ModelData.Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genres: List<ModelData.Genre>,
    actors: List<ModelData.Actor>
): List<ModelData.Movie> {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        (ModelData.Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            poster = jsonMovie.posterPicture,
            backdrop = jsonMovie.backdropPicture,
            ratings = jsonMovie.ratings,
            numberOfRatings = jsonMovie.votesCount,
            minimumAge = if (jsonMovie.adult) 16 else 13,
            runtime = jsonMovie.runtime,
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = jsonMovie.actors.map {
                actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
            },
            like = false
        ))
    }
}