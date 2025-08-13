package com.example.data.repository.details

import com.example.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>>
}