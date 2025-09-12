package com.example.movieapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.feature.details.MovieDetailsScreen
import com.example.feature.home.HomeScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MovieAppNavigation() {
    val backStack = rememberNavBackStack(HomeMovie)
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull()  },
        sceneStrategy = listDetailStrategy,
        entryProvider = entryProvider {
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
        },
    )
}

