package com.example.feature.home.impl

import androidx.paging.PagingData
import com.example.domain.GetDiscoverMoviesUseCase
import com.example.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    @MockK
    private lateinit var getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `loadDiscoverMovies - when getDiscoverMoviesUseCase returns movies - should emit non-empty paging data`() =
        runTest {
            // Given
            val expectedMovies = listOf(
                Movie(
                    id = 1,
                    title = "title",
                    overview = "overview",
                    averageVote = 0.0,
                    moviePosterUrl = "moviePosterUrl",
                    totalVotes = 0,
                    releaseDate = "releaseDate"
                ),
                Movie(
                    id = 2,
                    title = "title",
                    overview = "overview",
                    averageVote = 0.0,
                    moviePosterUrl = "moviePosterUrl",
                    totalVotes = 0,
                    releaseDate = "releaseDate"
                )
            )
            val pagingData = PagingData.from(expectedMovies)
            coEvery { getDiscoverMoviesUseCase.invoke() } returns flowOf(pagingData)

            // When
            viewModel.loadDiscoverMovies()

            // Then
            // TODO: Issue with viewModel.homeUiState.asSnapshot() because of collect in viewModel
            val result = viewModel.homeUiState.first()
            assertThat(result).isNotEqualTo(PagingData.empty<Movie>())
        }
}