package com.example.data.repository.di

import com.example.data.repository.utils.Dispatcher
import com.example.data.repository.utils.MovieDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(MovieDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}