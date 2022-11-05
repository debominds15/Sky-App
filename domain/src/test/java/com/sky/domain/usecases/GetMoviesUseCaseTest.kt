package com.sky.domain.usecases

import com.google.common.truth.Truth
import com.sky.data.repository.FakeMovieRepositoryImpl
import com.sky.domain.common.Result
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesUseCaseTest {

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var fakeMovieListRepository: FakeMovieRepositoryImpl

    @Before
    fun setUp() {
        fakeMovieListRepository = FakeMovieRepositoryImpl()
        getMoviesUseCase = GetMoviesUseCase(fakeMovieListRepository)
    }

    @Test
    fun `Get Movies List, correct movie list return`(): Unit = runBlocking {
        val titles = listOf("Movie 1", "Movie 2", "Movie 3")

        getMoviesUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    result.data.forEachIndexed { index, film ->
                        Truth.assertThat(film.title == titles[index]).isTrue()
                    }
                }
                else -> {

                }
            }
        }

    }

    @Test
    fun `Get Movies List, incorrect movie list return` (): Unit = runBlocking{
        val titles = listOf("Movie 10", "Movie 20", "Movie 30")

        getMoviesUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    result.data.forEachIndexed { index, film ->
                        //assert(film.title != titles[index])
                        Truth.assertThat(film.title == titles[index]).isFalse()
                    }
                }
                else -> {

                }
            }
        }
    }
}