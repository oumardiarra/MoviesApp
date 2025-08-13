package com.example.domain

import androidx.paging.PagingData
import com.example.data.repository.home.HomeRepository
import com.example.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiscoverMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> = homeRepository.getDiscoverMovies()
}