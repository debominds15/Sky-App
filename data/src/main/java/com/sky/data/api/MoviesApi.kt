package com.sky.data.api

import com.sky.data.entities.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApi {
    @GET("/api/movies")
    suspend fun getMovies(): Response<MovieResponse>
}