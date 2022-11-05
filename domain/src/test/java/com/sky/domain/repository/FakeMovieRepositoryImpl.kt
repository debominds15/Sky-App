package com.sky.data.repository

import com.sky.domain.common.Result
import com.sky.domain.entities.Film
import com.sky.domain.repositories.MovieRepository

class FakeMovieRepositoryImpl: MovieRepository {
    private val movieResult = Film(1, "Movie 1", 2019, "Action", "https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg")
    private val movieResult1 = Film(2, "Movie 2", 2019, "Action", "https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg")
    private val movieResult2 = Film(3, "Movie 3", 2019, "Sci-Fi", "https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg")
    private val listOfMovies = listOf(movieResult, movieResult1, movieResult2)

    override suspend fun getMovies(): Result<List<Film>> {
        return Result.Success(listOfMovies)
    }
}