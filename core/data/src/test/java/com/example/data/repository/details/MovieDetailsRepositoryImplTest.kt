package com.example.data.repository.details

import app.cash.turbine.test
import com.example.model.MovieDetails
import com.example.network.model.MovieDetailsDto
import com.example.network.model.MovieDetailsDto.CreditsDto
import com.example.network.model.MovieDetailsDto.VideosDto
import com.example.network.service.MovieService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class MovieDetailsRepositoryImplTest {

    private val movieService: MovieService = mockk()
    private lateinit var repository: MovieDetailsRepositoryImpl

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository = MovieDetailsRepositoryImpl(
            movieService = movieService,
            ioDispatcher = coroutinesRule.testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getMovieDetails - when service returns error - should return error`() = runTest {
        // Given
        val mockedError = mockk<HttpException>().apply {
            every { code() } returns 500
        }
        coEvery { movieService.fetchMovieDetails(movieId = any()) } throws (mockedError)

        // When
        val response = repository.getMovieDetails(movieId = 55)
        // Then
        response.test {
            val item = awaitItem()
            assertThat(item.isFailure).isTrue
            awaitComplete()
        }
    }

    @Test
    fun `getMovieDetails - when service returns data - should return data`() = runTest {
        // Given
        coEvery { movieService.fetchMovieDetails(movieId = any()) } returns (
                MovieDetailsDto(
                    title = "title",
                    overview = "overview",
                    voteCount = 5,
                    voteAverage = 55.59,
                    releaseDate = "releaseDate",
                    backdropPath = "backdropPath",
                    posterPath = "posterPath",
                    movieGenres = listOf(
                        MovieDetailsDto.GenresDto(
                            name = "name"
                        )
                    ),
                    credits = CreditsDto(
                        cast = listOf(
                            CreditsDto.CreditsCastDto(
                                name = "name",
                                character = "character",
                                profilePath = "profilePath"
                            )
                        ),
                    ),
                    videos = VideosDto(
                        results = listOf(
                            VideosDto.VideoDto(
                                key = "key"
                            )
                        )
                    )
                )
                )

        // When
        val response = repository.getMovieDetails(movieId = 55)

        // Then
        response.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(
                Result.success(
                    MovieDetails(
                        title = "title",
                        overview = "overview",
                        voteCount = 5,
                        voteAverage = 55.59,
                        releaseDate = "releaseDate",
                        backdropPath = "https://image.tmdb.org/t/p/w500/backdropPath",
                        posterPath = "https://image.tmdb.org/t/p/w500/posterPath",
                        movieGenres = listOf(
                            MovieDetails.Genres(
                                name = "name"
                            )
                        ),
                        credits = MovieDetails.Credits(
                            cast = listOf(
                                MovieDetails.Credits.CreditsCast(
                                    name = "name",
                                    character = "character",
                                    profilePath = "https://image.tmdb.org/t/p/w500/profilePath"
                                )
                            ),
                        ),
                        videos = MovieDetails.Videos(
                            results = listOf(
                                MovieDetails.Videos.Video(
                                    key = "key"
                                )
                            )
                        )
                    )
                )
            )
            awaitComplete()
        }
    }
}