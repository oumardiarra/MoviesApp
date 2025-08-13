package com.example.feature.details

import com.example.model.MovieDetails

sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Ready(val movieDetails: MovieDetails) : MovieDetailsUiState()
    data object Idle : MovieDetailsUiState()
    data object Error : MovieDetailsUiState()
}