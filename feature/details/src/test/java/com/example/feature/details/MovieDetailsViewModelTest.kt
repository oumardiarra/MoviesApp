package com.example.feature.details

import android.net.http.HttpException
import app.cash.turbine.test
import com.example.domain.GetMovieDetailsUseCase
import com.example.model.MovieDetails
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class MovieDetailsViewModelTest {

    @MockK
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @InjectMockKs
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getMovieDetails - when getMovieDetails returns data - should set state to Ready with data`() =
        runTest {
            // Given
            val movieDetails = mockk<MovieDetails>()
            val flowData = flowOf(
                Result.success(movieDetails)
            )
            coEvery { getMovieDetailsUseCase.invoke(movieId = any()) }.returns(flowData)
            // When
            viewModel.getMovieDetails(movieId = 5)

            // Then
            flowData.test{
                awaitItem()
                assertThat(viewModel.movieDetailsUiState.value).isEqualTo(
                    MovieDetailsUiState.Ready(movieDetails = movieDetails)
                )
                awaitComplete()
            }


        }
}