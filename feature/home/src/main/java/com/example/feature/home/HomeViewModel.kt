package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.GetDiscoverMoviesUseCase
import com.example.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiscoverMovieUsecase: GetDiscoverMoviesUseCase,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val homeUiState = _homeUiState.asStateFlow()

    fun loadDiscoverMovies() = viewModelScope.launch {
        getDiscoverMovieUsecase
            .invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope).collect {
                _homeUiState.value = it
            }
    }
}