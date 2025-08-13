package com.example.domain

import com.example.data.repository.details.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
) {
    suspend operator fun invoke(movieId: Int) =
        movieDetailsRepository.getMovieDetails(movieId = movieId)
}