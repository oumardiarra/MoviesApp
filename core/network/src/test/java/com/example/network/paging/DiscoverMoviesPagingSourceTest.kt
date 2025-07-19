package com.example.network.paging

import androidx.paging.PagingSource
import com.example.model.Movie
import com.example.network.service.MovieService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

import retrofit2.HttpException

class DiscoverMoviesPagingSourceTest {
    private lateinit var movieService: MovieService
    private lateinit var mockWebServer: MockWebServer

    private lateinit var discoverMoviesPagingSource: DiscoverMoviesPagingSource


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        movieService = mockk(relaxed = true)
        discoverMoviesPagingSource = DiscoverMoviesPagingSource(
            movieService = movieService,
        )
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
            every { message() } returns "Server Error"
        }
        coEvery { movieService.discoverMovies(page = any()) }.throws(mockedError)

        // When
        val result = discoverMoviesPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isEqualTo(PagingSource.LoadResult.Error<Int, Movie>(mockedError))

    }
}