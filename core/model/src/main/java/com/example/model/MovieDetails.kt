package com.example.model

data class MovieDetails(
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val backdropPath: String,
    val posterPath: String,
    val movieGenres: List<Genres>,
    val credits: Credits,
    val videos: Videos,
) {
    data class Genres(val name: String)
    data class Credits(val cast: List<CreditsCast>) {
        data class CreditsCast(
            val name: String,
            val character: String,
            val profilePath: String?,
        )
    }

    data class Videos(val results: List<Video>) {
        data class Video(val key: String)
    }
}