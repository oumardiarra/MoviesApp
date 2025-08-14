package com.example.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.model.MovieDetails

@Composable
internal fun MovieDescriptionSection(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier,
) {
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
    }
}