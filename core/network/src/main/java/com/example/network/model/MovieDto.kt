package com.example.network.model


import com.example.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"
@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerialName("vote_average")
    val averageVote: Double,
    @SerialName("poster_path")
    val moviePosterUrl: String,
    @SerialName("vote_count")
    val totalVotes: Int,
    @SerialName("release_date")
    val releaseDate: String,
)

fun MovieDto.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    averageVote = averageVote,
    moviePosterUrl = POSTER_PREFIX + moviePosterUrl,
    totalVotes = totalVotes,
    releaseDate = releaseDate,
)
