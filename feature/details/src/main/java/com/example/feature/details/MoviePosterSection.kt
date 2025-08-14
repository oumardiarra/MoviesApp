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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.model.MovieDetails
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
internal fun MoviePosterSection(
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