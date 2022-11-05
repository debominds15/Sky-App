package com.sky.data.mappers

import com.sky.data.entities.Movie
import com.sky.domain.entities.Film
import javax.inject.Inject

class MovieApiResponseMapper @Inject constructor() {
    fun toFilmList(movies: List <Movie>): List<Film> {
        return movies.map {
            Film(
                it.id,
                it.title,
                it.year,
                it.genre,
                it.poster
            )
        }
    }
}