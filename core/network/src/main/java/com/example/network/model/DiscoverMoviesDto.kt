package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DiscoverMoviesDto(
    val page: Int,
    val results: List<MovieDto>,
)
