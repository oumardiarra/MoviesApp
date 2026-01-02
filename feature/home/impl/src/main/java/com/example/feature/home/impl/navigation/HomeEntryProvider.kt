package com.example.feature.home.impl.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.ui.Alignment
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.feature.details.api.navigation.MovieDetails
import com.example.feature.home.api.navigation.HomeMovie
import com.example.feature.home.impl.HomeScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.homeEntry(
    backStack: NavBackStack<NavKey>
) {
    entry<HomeMovie>(
        metadata = ListDetailSceneStrategy.listPane(
            detailPlaceholder = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Choose a movie from the list.")
                }
            }
        )
    ) {
        HomeScreen(
            onNavigateToMovieDetails = { movieId ->
                backStack.add(MovieDetails(movieId = movieId))
            }
        )
    }
}