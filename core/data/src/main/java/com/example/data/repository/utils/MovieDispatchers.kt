package com.example.data.repository.utils

import jakarta.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(value = RUNTIME)
annotation class Dispatcher(val movieDispatcher: MovieDispatchers)

enum class MovieDispatchers {
    IO,
}