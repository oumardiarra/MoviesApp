package com.example.feature.details.impl.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.feature.details.api.navigation.MovieDetails
import com.example.feature.details.impl.MovieDetailsScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.detailsEntry(
    backStack: NavBackStack<NavKey>
) {
    entry<MovieDetails>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) { route ->
        MovieDetailsScreen(
            movieId = route.movieId,
            onNavigateBack = {
                backStack.removeLastOrNull()
            },
        )
    }
}