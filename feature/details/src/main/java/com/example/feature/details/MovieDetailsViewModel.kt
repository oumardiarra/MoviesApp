package com.example.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetailsUiState =
        MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Idle)
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        _movieDetailsUiState.value = MovieDetailsUiState.Loading
        viewModelScope.launch {
            val result = movieDetailsUseCase.invoke(movieId = movieId)
            result.collect { result ->
                result.onSuccess { movieDetails ->
                    _movieDetailsUiState.value =
                        MovieDetailsUiState.Ready(movieDetails = movieDetails)
                }.onFailure {
                    _movieDetailsUiState.value = MovieDetailsUiState.Error
                }
            }
        }
    }
}