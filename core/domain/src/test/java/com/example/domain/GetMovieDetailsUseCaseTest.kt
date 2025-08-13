package com.example.domain

import app.cash.turbine.test
import com.example.data.repository.details.MovieDetailsRepository
import com.example.model.MovieDetails
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {
    @MockK
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    @InjectMockKs
    private lateinit var movieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke - should return movies details`() = runTest {
        // Given
        val movieDetails = mockk<MovieDetails>()
        val flowMoviesData = flowOf(
            Result.success(
                movieDetails
            )
        )
        coEvery { movieDetailsRepository.getMovieDetails(movieId = 3) } returns (flowMoviesData)

        // When
        val data = movieDetailsUseCase.invoke(movieId = 3)

        // Then
        data.test {
            assertThat(awaitItem()).isEqualTo(Result.success(movieDetails))
            awaitComplete()
        }
    }
}