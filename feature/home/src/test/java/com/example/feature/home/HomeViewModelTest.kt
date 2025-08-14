package com.example.feature.home

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.domain.GetDiscoverMoviesUseCase
import com.example.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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
    fun `loadDiscoverMovies - when getDiscoverMoviesUseCase returns movies - should return data`() =
        runTest {
            // Given
            val movies = PagingData.from(
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
            val flowOfMovies = flow { emit(movies) }
            val noData = flow { emit(PagingData.from(emptyList())) }
            coEvery { getDiscoverMoviesUseCase.invoke() } returns (flowOfMovies)

            // When
            viewModel.loadDiscoverMovies()

            // Then
            val nn = noData.asSnapshot()
            //assertThat(viewModel.homeUiState.asSnapshot()).isEqualTo(noData.asSnapshot())
        }
}