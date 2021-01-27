package ru.eremite.myapplication.utils

import ru.eremite.myapplication.domain.models.Actor
import ru.eremite.myapplication.domain.models.Genre
import ru.eremite.myapplication.domain.models.Movie
import ru.eremite.myapplication.presentation.models.PresentationModelData

object ModelsMapper {
    fun toActorPresentation(actor: Actor) =
        PresentationModelData.ActorPresentation(actor.id, actor.name, actor.picture)

    fun toGenrePresentation(genre:Genre) = PresentationModelData.GenrePresentation(genre.id, genre.name)

    fun toMoviePresentation(movie: Movie) = PresentationModelData.MoviePresentation(movie.id,
        movie.title,
        movie.overview,
        movie.poster,
        movie.backdrop,
        movie.ratings,
        movie.numberOfRatings,
        movie.minimumAge,
        movie.runtime,
        movie.like,
        movie.genres.map { toGenrePresentation(it) })
}