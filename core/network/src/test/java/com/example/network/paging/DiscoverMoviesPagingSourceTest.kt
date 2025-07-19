package com.example.network.paging

import androidx.paging.PagingSource
import com.example.model.Movie
import com.example.network.model.DiscoverMoviesDto
import com.example.network.model.MovieDto
import com.example.network.service.MovieService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

import retrofit2.HttpException

class DiscoverMoviesPagingSourceTest {
    @MockK
    private lateinit var movieService: MovieService

    @InjectMockKs
    private lateinit var discoverMoviesPagingSource: DiscoverMoviesPagingSource

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockWebServer = MockWebServer()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `load - when service returns exception - should return Error`() = runTest {
        // Given
        val mockedError = mockk<HttpException>().apply {
            every { code() } returns 500
        }
        coEvery { movieService.discoverMovies(page = any()) }.throws(mockedError)

        // When
        val result = discoverMoviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        // Then
        assertThat(result).isEqualTo(PagingSource.LoadResult.Error<Int, Movie>(mockedError))
    }

    @Test
    fun `load - when service returns movies - should return movies`() = runTest {
        // Given
        val moviesDto =
            coEvery { movieService.discoverMovies(page = any()) }.returns(
                DiscoverMoviesDto(
                    page = 1,
                    results = listOf(
                        MovieDto(
                            id = 1,
                            title = "title",
                            overview = "overview",
                            averageVote = 0.0,
                            moviePosterUrl = "moviePosterUrl",
                            totalVotes = 0,
                            releaseDate = "releaseDate"
                        )
                    )
                )
            )

        // When
        val result = discoverMoviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false,
            )
        )

        // Then
        assertThat(result).isEqualTo(
            PagingSource.LoadResult.Page(
                prevKey = null,
                nextKey = 2,
                data = listOf(
                    Movie(
                        id = 1,
                        title = "title",
                        overview = "overview",
                        averageVote = 0.0,
                        moviePosterUrl = "moviePosterUrl",
                        totalVotes = 0,
                        releaseDate = "releaseDate"
                    )
                )
            )
        )
    }
}