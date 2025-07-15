package com.example.network.service

import com.example.network.model.DiscoverMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("3/discover/movie?language=en-US&sort_by=popularity.desc")
    suspend fun discoverMovies (@Query("page") page: Int = 1): DiscoverMoviesDto
}