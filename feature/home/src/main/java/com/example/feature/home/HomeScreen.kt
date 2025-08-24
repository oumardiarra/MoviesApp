package com.example.feature.home

import android.widget.Toast
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.model.Movie
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMovieDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.loadDiscoverMovies()
    }

    val moviesData = viewModel.homeUiState.collectAsLazyPagingItems()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text(text = stringResource(id = R.string.app_bar_home_title)) }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (moviesData.loadState.refresh) {
                LoadState.Loading -> CircularProgressIndicator()
                is LoadState.Error -> {
                    Toast.makeText(context, "loadState.refresh Error", Toast.LENGTH_LONG).show()
                }

                else -> {
                    HomeScreenScreenContent(
                        movieListData = moviesData,
                        onNavigateToMovieDetails = onNavigateToMovieDetails
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenScreenContent(
    modifier: Modifier = Modifier,
    movieListData: LazyPagingItems<Movie>,
    onNavigateToMovieDetails: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(movieListData.itemCount) { movieIndex ->
            val movieItem = movieListData[movieIndex]
            movieItem?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    onClick = { onNavigateToMovieDetails(movieItem.id) }
                ) {
                    AsyncImage(
                        model = movieItem.moviePosterUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movieItem.title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movieItem.releaseDate,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        item {
            if (movieListData.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenScreenContentPreview() {
    val movies = listOf(
        Movie(
            id = 1,
            title = "title",
            overview = "overview",
            averageVote = 0.0,
            moviePosterUrl = "https://image.tmdb.org/t/p/w500/yvirUYrva23IudARHn3mMGVxWqM.jpg",
            totalVotes = 0,
            releaseDate = "releaseDate"
        ),
        Movie(
            id = 2,
            title = "title",
            overview = "overview",
            averageVote = 0.0,
            moviePosterUrl = "https://image.tmdb.org/t/p/w500/yvirUYrva23IudARHn3mMGVxWqM.jpg",
            totalVotes = 0,
            releaseDate = "releaseDate"
        ),
        Movie(
            id = 3,
            title = "title",
            overview = "overview",
            averageVote = 0.0,
            moviePosterUrl = "https://image.tmdb.org/t/p/w500/yvirUYrva23IudARHn3mMGVxWqM.jpg",
            totalVotes = 0,
            releaseDate = "releaseDate"
        )
    )
    HomeScreenScreenContent(
        movieListData = flowOf(value = PagingData.from(movies)).collectAsLazyPagingItems(),
        onNavigateToMovieDetails = {},
    )
}