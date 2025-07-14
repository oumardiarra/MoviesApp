package com.example.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val averageVote: Double,
    val moviePosterUrl: String,
    val totalVotes: Int,
    val releaseDate: String,
)
