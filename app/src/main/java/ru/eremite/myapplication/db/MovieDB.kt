package ru.eremite.myapplication.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.eremite.myapplication.ViewModel.PresentationModelData
import ru.eremite.myapplication.utils.MovieRepository

class MovieDB(applicationContext: Context) : MovieRepository() {
    private val moviesDb = DataBase.create(applicationContext)

    override suspend fun loadMovies(currentPage: Int): List<PresentationModelData.MoviePresentation> =
        moviesDb.moviesDAO.getAll().map { toMovie(it) }

    override suspend fun loadMoviesAgeRuntime(listMovies: List<PresentationModelData.MoviePresentation>): List<PresentationModelData.MoviePresentation> =
        moviesDb.moviesDAO.getAll().map { toMovie(it) }

    override fun findMovie(
        idMovie: Int,
        moviesList: List<PresentationModelData.MoviePresentation>
    ): PresentationModelData.MoviePresentation? {
        TODO("Not yet implemented")
    }

    override suspend fun findMovie(idMovie: Int): PresentationModelData.MoviePresentation =
        toMovie(moviesDb.moviesDAO.getMovieById(idMovie))

    override suspend fun getActors(idMovie: Int): List<PresentationModelData.ActorPresentation> =
        moviesDb.moviesDAO.getMovieById(idMovie).actors.map { toActor(it) }

    override suspend fun findMoviesByName(
        name: String,
        currentPage: Int
    ): List<PresentationModelData.MoviePresentation> {
        TODO("Not yet implemented")
    }

    suspend fun updateActorDataInDB(
        movieId: Int,
        actors: List<PresentationModelData.ActorPresentation>
    ): Boolean =
        withContext(Dispatchers.IO) {
            moviesDb.movieActorsDAO.deleteActorsMovie(movieId)
            actors.forEachIndexed { index, currentActor ->
                if (moviesDb.actorsDAO.getActorById(currentActor.id) == null) moviesDb.actorsDAO.insert(
                    toActorEntity(currentActor)
                )
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
                    if (moviesDb.genresDAO.getGenreById(it.id) == null) moviesDb.genresDAO.insert(
                        toGenreEntity(it)
                    )
                    moviesDb.movieGenresDAO.insert(MovieGenreEntity(currentMovie.id, it.id))
                }

            }
            true
        }

    private fun toMovie(movieEntity: MovieWithActorsGenres): PresentationModelData.MoviePresentation {
        return PresentationModelData.MoviePresentation(
            id = movieEntity.movieEntity.movieId,
            title = movieEntity.movieEntity.title,
            overview = movieEntity.movieEntity.overview,
            poster = movieEntity.movieEntity.poster,
            backdrop = movieEntity.movieEntity.backdrop,
            ratings = movieEntity.movieEntity.ratings,
            minimumAge = movieEntity.movieEntity.minimumAge,
            runtime = movieEntity.movieEntity.runtime,
            like = movieEntity.movieEntity.like,
            genres = movieEntity.genres.map { toGenre(it) }
        )

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