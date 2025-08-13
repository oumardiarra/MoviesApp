package com.example.network.model

import com.example.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val title: String,
    val overview: String,
    @SerialName(value = "vote_average")
    val voteAverage: Double,
    @SerialName(value = "vote_count")
    val voteCount: Int,
    @SerialName(value = "release_date")
    val releaseDate: String,
    @SerialName(value = "backdrop_path")
    val backdropPath: String,
    @SerialName(value = "poster_path")
    val posterPath: String,
    @SerialName(value = "genres")
    val movieGenres: List<GenresDto>,
    val credits: CreditsDto,
    val videos: VideosDto,
) {
    @Serializable
    data class GenresDto(val name: String)

    @Serializable
    data class CreditsDto(val cast: List<CreditsCastDto>) {
        @Serializable
        data class CreditsCastDto(
            val name: String,
            val character: String,
            @SerialName(value = "profile_path")
            val profilePath: String?,
        )
    }

    @Serializable
    data class VideosDto(val results: List<VideoDto>) {
        @Serializable
        data class VideoDto(
            val key: String,
        )
    }
}

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    title = title,
    overview = overview,
    voteAverage = voteAverage,
    voteCount = voteCount,
    releaseDate = releaseDate,
    backdropPath = backdropPath,
    posterPath = posterPath,
    movieGenres = movieGenres.map {
        MovieDetails.Genres(
            name = it.name,
        )
    },
    credits = MovieDetails.Credits(
        cast = credits.cast.map { it ->
            MovieDetails.Credits.CreditsCast(
                name = it.name,
                character = it.character,
                profilePath = it.profilePath,
            )
        }
    ),
    videos = MovieDetails.Videos(
        results = videos.results.map { it ->
            MovieDetails.Videos.Video(
                key = it.key,
            )
        }
    )
)