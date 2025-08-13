package com.example.domain

import androidx.paging.PagingData
import com.example.data.repository.home.HomeRepository
import com.example.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class GetDiscoverMoviesUseCaseTest {
    @MockK
    private lateinit var homeRepository: HomeRepository

    @InjectMockKs
    private lateinit var getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke - should return movies data`() = runTest {
        // Given
        val movies = PagingData.from(data = listOf(mockk<Movie>()))
        val flowOfMovies = flow { emit(movies) }
        coEvery { homeRepository.getDiscoverMovies() } returns (flowOfMovies)

        // When
        val result = getDiscoverMoviesUseCase.invoke()

        // Then
        assertThat(result).isEqualTo(flowOfMovies)
    }

}