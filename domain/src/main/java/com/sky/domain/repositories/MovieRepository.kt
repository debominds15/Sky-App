package com.sky.domain.repositories

import com.sky.domain.entities.Film
import com.sky.domain.common.Result

interface MovieRepository {
    suspend fun getMovies(): Result<List<Film>>
}