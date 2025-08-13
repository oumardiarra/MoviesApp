package com.example.data.repository.di

import com.example.data.repository.details.MovieDetailsRepository
import com.example.data.repository.details.MovieDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailsRepositoryModule {

    @Binds
    abstract fun bindMovieDetailsRepository(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository
}