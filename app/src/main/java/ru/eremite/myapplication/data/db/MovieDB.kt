package ru.eremite.myapplication.data.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.eremite.myapplication.data.db.models.*
import ru.eremite.myapplication.presentation.models.PresentationModelData

class MovieDB(applicationContext: Context) {
    private val moviesDb = DataBase.create(applicationContext)

    suspend fun loadMovies(): List<PresentationModelData.MoviePresentation> =
        moviesDb.moviesDAO.getAll().map { toMovie(it) }

    suspend fun findMovie(idMovie: Int): PresentationModelData.MoviePresentation =
        toMovie(moviesDb.moviesDAO.getMovieById(idMovie))

    suspend fun getActors(idMovie: Int): List<PresentationModelData.ActorPresentation> =
        moviesDb.moviesDAO.getMovieById(idMovie).actors.map { toActor(it) }

    suspend fun updateActorDataInDB(
        movieId: Int,
        actors: List<PresentationModelData.ActorPresentation>
    ): Boolean =
        withContext(Dispatchers.IO) {
            moviesDb.movieActorsDAO.deleteActorsMovie(movieId)
            actors.forEachIndexed { index, currentActor ->
                moviesDb.movieActorsDAO.insert(MovieActorEntity(movieId, currentActor.id, index))
            }
            true
        }

    suspend fun updateDataInDB(movies: List<PresentationModelData.MoviePresentation>): Boolean =
        withContext(Dispatchers.IO) {
            moviesDb.movieActorsDAO.deleteAll()
            moviesDb.movieGenresDAO.deleteAll()
            moviesDb.genresDAO.deleteAll()
            moviesDb.moviesDAO.deleteAll()
            movies.forEach { currentMovie ->
                moviesDb.moviesDAO.insert(toMovieEntity(currentMovie))
                currentMovie.genres.forEach {
                    moviesDb.movieGenresDAO.insert(MovieGenreEntity(currentMovie.id, it.id))
                }

            }
            true
        }

    fun getCacheUnderUpdate(): Flow<List<PresentationModelData.MoviePresentation>> = flow {
        moviesDb.cacheUnderUpdateDAO.getCacheUnderUpdate().collect { listMovieEntity ->
            emit(listMovieEntity.map { toMovie(it) })
        }
    }


    private fun toMovie(movieEntity: MovieWithActorsGenres): PresentationModelData.MoviePresentation {
        return movieEntity.movieEntity?.let { movieEntityP ->
            with(movieEntityP) {
                PresentationModelData.MoviePresentation(
                    id = movieId,
                    title = title,
                    overview = overview,
                    poster = poster,
                    backdrop = backdrop,
                    ratings = ratings,
                    minimumAge = minimumAge,
                    runtime = runtime,
                    like = like,
                    genres = movieEntity.genres.map { toGenre(it) }
                )
            }
        }

    }

    private fun toActor(actorEntity: ActorEntity) = PresentationModelData.ActorPresentation(
        id = actorEntity._id,
        name = actorEntity.title,
        picture = actorEntity.picture
    )

    private fun toGenre(genryEntity: GenreEntity) = PresentationModelData.GenrePresentation(
        id = genryEntity._id,
        name = genryEntity.title,
    )

    private fun toMovieEntity(movie: PresentationModelData.MoviePresentation) = MovieEntity(
        0,
        movieId = movie.id,
        title = movie.title,
        overview = movie.overview,
        poster = movie.poster ?: "",
        backdrop = movie.backdrop ?: "",
        ratings = movie.ratings,
        minimumAge = movie.minimumAge,
        runtime = movie.runtime,
        like = movie.like
    )

    private fun toActorEntity(actor: PresentationModelData.ActorPresentation) = ActorEntity(
        _id = actor.id,
        title = actor.name,
        picture = actor.picture
    )

    private fun toGenreEntity(genre: PresentationModelData.GenrePresentation) = GenreEntity(
        _id = genre.id,
        title = genre.name,
    )
}