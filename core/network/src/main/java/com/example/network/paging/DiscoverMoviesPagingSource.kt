package com.example.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.Movie
import com.example.network.model.toMovie
import com.example.network.service.MovieService

class DiscoverMoviesPagingSource(
    private val movieService: MovieService,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = movieService.discoverMovies(page = nextPageNumber)
            LoadResult.Page(
                prevKey = null,
                data = response.results.map { it.toMovie() },
                nextKey = response.page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}