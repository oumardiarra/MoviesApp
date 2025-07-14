package com.example.data.repository.home

import com.example.model.Movie
import kotlinx.coroutines.flow.Flow

sealed class HomeRepositoryImpl: HomeRepository {
    override suspend fun getDiscoverMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}