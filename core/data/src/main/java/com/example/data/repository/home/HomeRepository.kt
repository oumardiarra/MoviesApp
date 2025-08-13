package com.example.data.repository.home

import androidx.paging.PagingData
import com.example.model.Movie
import kotlinx.coroutines.flow.Flow

sealed interface HomeRepository {
    suspend fun getDiscoverMovies(): Flow<PagingData<Movie>>
}