package com.example.feature.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.model.MovieDetails

@Composable
internal fun MovieCastSection(
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
                        .
                        width(140.dp)
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