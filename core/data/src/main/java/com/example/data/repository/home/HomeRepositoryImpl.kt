package com.example.data.repository.home

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.repository.utils.Dispatcher
import com.example.data.repository.utils.MovieDispatchers
import com.example.model.Movie
import com.example.network.paging.DiscoverMoviesPagingSource
import com.example.network.service.MovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    @Dispatcher(MovieDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    companion object {
        private const val PAGE_SIZE = 20
    }

    override suspend fun getDiscoverMovies(): Flow<PagingData<Movie>> =
        withContext(context = ioDispatcher) {
            Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false,
                ),
                pagingSourceFactory = { DiscoverMoviesPagingSource(movieService = movieService) }
            ).flow
        }
}