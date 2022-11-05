package com.sky.demo.presentation.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.domain.common.Result
import com.sky.domain.entities.Film
import com.sky.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _movies = MutableLiveData<List<Film>>()
    val movies = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _filteredMovies = MutableLiveData<List<Film>>()
    val filteredMovies = _filteredMovies

    init {
        getMovies()
    }

    private fun getMovies() {
        _dataLoading.postValue(true)
        movieUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    _dataLoading.postValue(false)
                    _movies.value = result.data ?: emptyList()
                }
                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _movies.value = emptyList()
                    _error.postValue(result.exception)
                }
                else -> {
                    _dataLoading.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }

    open fun filterMovies(text: String, movies: List<Film>) {
        viewModelScope.launch {
            val filteredMovies = movies.filter {
                it.title.lowercase().contains(text.lowercase()) || it.genre.lowercase()
                    .contains(text.lowercase())
            }

            _filteredMovies.value = emptyList()
            _filteredMovies.value = filteredMovies ?: emptyList()
        }
    }
}