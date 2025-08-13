package com.example.data.repository.details

import com.example.data.repository.utils.Dispatcher
import com.example.data.repository.utils.MovieDispatchers
import com.example.model.MovieDetails
import com.example.network.model.toMovieDetails
import com.example.network.service.MovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    @Dispatcher(MovieDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>> =
        flow {
            try {
                val response = movieService.fetchMovieDetails(movieId = movieId)
                emit(Result.success(response.toMovieDetails()))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }.flowOn(ioDispatcher)
}