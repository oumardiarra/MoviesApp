package com.example.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.example.model.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onNavigateBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(movieId = movieId)
    }

    val uiState by viewModel.movieDetailsUiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        MovieDetailsUiState.Error -> Unit
        MovieDetailsUiState.Idle -> Unit
        MovieDetailsUiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailsUiState.Ready -> {
            MovieDetailsScreenContent(
                movieDetails = state.movieDetails,
                onNavigateBack = onNavigateBack,
            )
        }
    }
}

@Composable
fun MovieDetailsScreenContent(
    movieDetails: MovieDetails,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        MoviePosterSection(
            movieDetails = movieDetails,
            onNavigateBack = onNavigateBack,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(28.dp))
        MovieDescriptionSection(
            movieDetails = movieDetails,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        MovieCastSection(
            cast = movieDetails.credits.cast,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@PreviewLightDark
@Composable
private fun MovieDetailsScreenContentPreview() {
    val movieDetail = MovieDetails(
        title = "East of Wall East of Wall",
        overview = "overview overviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverviewoverview",
        voteCount = 5,
        voteAverage = 55.59,
        releaseDate = "releaseDate",
        backdropPath = "backdropPath",
        posterPath = "posterPath",
        movieGenres = listOf(
            MovieDetails.Genres(
                name = "nale nal nale"
            ),
            MovieDetails.Genres(
                name = "name"
            ),
            MovieDetails.Genres(
                name = "name"
            ),
            MovieDetails.Genres(
                name = "name"
            )
        ),
        credits = MovieDetails.Credits(
            cast = listOf(
                MovieDetails.Credits.CreditsCast(
                    name = "name",
                    character = "character",
                    profilePath = "profilePath"
                ),
                MovieDetails.Credits.CreditsCast(
                    name = "name",
                    character = "character",
                    profilePath = "profilePath"
                )
            ),
        ),
        videos = MovieDetails.Videos(
            results = listOf(
                MovieDetails.Videos.Video(
                    key = "key"
                )
            )
        )
    )
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        MovieDetailsScreenContent(
            movieDetails = movieDetail,
            onNavigateBack = {},
        )
    }

}