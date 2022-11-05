package com.sky.data.repositories

import com.sky.data.api.MoviesApi
import com.sky.data.mappers.MovieApiResponseMapper
import com.sky.domain.common.Result
import com.sky.domain.entities.Film
import com.sky.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MoviesApi,
    private val mapper: dagger.Lazy<MovieApiResponseMapper>
) : MovieRepository {
    override suspend fun getMovies(): Result<List<Film>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getMovies()
                if (response.isSuccessful) {
                    return@withContext Result.Success(
                        mapper.get().toFilmList(response.body()!!.data)
                    )
                } else {
                    return@withContext Result.Error(response.message())
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e.message ?: "Exception found!!")
            }
        }
}