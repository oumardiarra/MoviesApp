package com.example.feature.details.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(val movieId: Int) : NavKey
