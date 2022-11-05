package com.sky.domain.usecases

import com.sky.domain.common.Result
import com.sky.domain.entities.Film
import com.sky.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

open class GetMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(): Flow<Result<List<Film>>> = flow {
        try {
            val movies = movieRepository.getMovies()
            emit(movies)
        } catch (e: Exception) {
            emit(Result.Error("Exception occurred!!!"))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}