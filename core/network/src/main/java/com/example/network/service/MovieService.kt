package com.example.network.service

import com.example.network.model.DiscoverMoviesDto
import com.example.network.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("3/discover/movie?language=en-US&sort_by=popularity.desc")
    suspend fun discoverMovies(@Query("page") page: Int = 1): DiscoverMoviesDto

    @GET("3/movie/{movie_id}?language=en-US")
    suspend fun fetchMovieDetails(
        @Path( "movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "credits,videos"
    ): MovieDetailsDto
}