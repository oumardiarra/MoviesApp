package com.example.movieapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeMovie : NavKey

@Serializable
data class MovieDetails(val movieId: Int) : NavKey
