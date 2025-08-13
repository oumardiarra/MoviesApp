package com.example.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.model.MovieDetails
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        MoviePosterSection(
            movieDetails = movieDetails,
            onNavigateBack = onNavigateBack,
        )
        Spacer(modifier.height(28.dp))
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                movieDetails.movieGenres.forEachIndexed { index, genre ->
                    Text(
                        text = genre.name,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    if (movieDetails.movieGenres.lastIndex != index) {
                        VerticalDivider(modifier = Modifier.height(16.dp))
                    }
                }
            }
            Spacer(modifier.height(16.dp))
            Text(
                text = movieDetails.overview,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier.height(16.dp))
            MovieCastSection(
                cast = movieDetails.credits.cast,
            )
        }
    }
}

@Composable
private fun MoviePosterSection(
    movieDetails: MovieDetails,
    onNavigateBack: () -> Unit,
) {
    var shouldPlayVideo by remember { mutableStateOf(false) }
    ConstraintLayout(modifier = Modifier.height(270.dp)) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .padding(start = 16.dp, top = 32.dp)
                .size(32.dp)
                .clickable { onNavigateBack() },

            )
        val (backgroundImageBox, dateAndRatingInfo, playTrailerBox) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black), startY = 100f
                    )
                )
                .constrainAs(backgroundImageBox) {}
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${movieDetails.backdropPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 0.7f
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(dateAndRatingInfo) {
                    bottom.linkTo(
                        anchor = backgroundImageBox.bottom,
                        margin = 24.dp
                    )
                    start.linkTo(anchor = backgroundImageBox.start)
                }
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(50.dp)
                        ),
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color.Yellow,
                        contentDescription = "stars",
                    )
                    Text(
                        text = movieDetails.voteAverage.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    VerticalDivider(modifier = Modifier.height(16.dp))
                    Text(
                        text = movieDetails.releaseDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 20.dp)
                .constrainAs(playTrailerBox) {
                    bottom.linkTo(backgroundImageBox.bottom)
                    end.linkTo(backgroundImageBox.end)
                }
        ) {
            IconButton(
                onClick = { shouldPlayVideo = true },
                modifier = Modifier
                    .background(color = Color(0xFFFF0000))
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play trailer",
                    tint = Color.White
                )
            }

        }
        if (shouldPlayVideo) {
            Dialog(onDismissRequest = { shouldPlayVideo = false }) {
                AndroidView(
                    factory = { context ->
                        // Creates view
                        YouTubePlayerView(context).apply {
                            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(
                                        movieDetails.videos.results.first().key,
                                        0f
                                    )
                                }
                            })
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun MovieCastSection(
    cast: List<MovieDetails.Credits.CreditsCast>,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = R.string.cast),
        style = MaterialTheme.typography.headlineMedium,
    )
    Spacer(modifier.height(12.dp))
    LazyRow {
        items(cast) {
            it.profilePath?.let { posterPath ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(140.dp)
                        .height(200.dp)
                        .padding(start = 8.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500/${posterPath}")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.loading_img),
                    )
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
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