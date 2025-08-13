package com.example.network.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadResult
import androidx.paging.testing.TestPager
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
        val discoverPagingSource = DiscoverMoviesPagingSource(movieService = movieService)
        val pager = TestPager(config = PagingConfig(pageSize = 50), discoverPagingSource)

        // When
        val result = pager.refresh()

        // Then
        assertThat(result).isEqualTo(LoadResult.Error<Int, Movie>(mockedError))
        assertThat(pager.getLastLoadedPage()).isNull()
    }

    @Test
    fun `load - when service returns movies - should return movies`() = runTest {
        // Given
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
            ),
        )
        val discoverPagingSource = DiscoverMoviesPagingSource(movieService = movieService)
        val pager = TestPager(config = PagingConfig(pageSize = 50), discoverPagingSource)

        // When
        val result = pager.refresh() as LoadResult.Page

        // Then
        assertThat(result.data)
            .containsExactly(
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
    }

    @Test
    fun `load - when service returns consecutive movies - should return movies`() = runTest {
        // Given
        coEvery { movieService.discoverMovies(page = any()) }.returnsMany(
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
            ),
            DiscoverMoviesDto(
                page = 2,
                results = listOf(
                    MovieDto(
                        id = 2,
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
        val discoverPagingSource = DiscoverMoviesPagingSource(movieService = movieService)
        val pager = TestPager(config = PagingConfig(pageSize = 50), discoverPagingSource)

        // When
        val result = with(pager) {
            refresh()
            append()
        } as LoadResult.Page

        // Then
        assertThat(result.data)
            .containsExactly(
                Movie(
                    id = 2,
                    title = "title",
                    overview = "overview",
                    averageVote = 0.0,
                    moviePosterUrl = "moviePosterUrl",
                    totalVotes = 0,
                    releaseDate = "releaseDate"
                ),
            )
    }
}