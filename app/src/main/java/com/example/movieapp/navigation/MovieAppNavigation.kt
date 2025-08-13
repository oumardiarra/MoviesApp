package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.feature.details.MovieDetailsScreen
import com.example.feature.home.HomeScreen

@Composable
fun MovieAppNavigation() {
    val backStack = remember { mutableStateListOf<Any>(HomeMovie) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<HomeMovie> {
                HomeScreen(
                    onNavigateToMovieDetails = { movieId ->
                        backStack.add(MovieDetails(movieId = movieId))
                    }
                )
            }
            entry<MovieDetails> { route ->
                MovieDetailsScreen(
                    movieId = route.movieId,
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                )
            }
        }
    )
}

