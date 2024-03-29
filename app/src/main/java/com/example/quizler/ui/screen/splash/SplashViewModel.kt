package com.example.quizler.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.usecase.HandleStartupDataUseCase
import com.example.quizler.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    networkRepository: INetworkRepository,
    private val handleStartupDataUseCase: HandleStartupDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState())
    val state = _state.combine(networkRepository.getHasInternetConnectionFlow()) { state, connectivity ->
        connectivity?.let {
            state.copy(hasConnection = connectivity)
        } ?: state
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SplashScreenState()
    )

    init {
        fetchData()
    }

    fun fetchData() {
        _state.update { it.copy(isDataFetchInProgress = true) }
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                handleStartupData()
            }
        }
    }

    private suspend fun handleStartupData() {
        handleStartupDataUseCase().collect { newState ->
            when (newState) {
                is State.Error -> {
                    handleError(newState)
                    return@collect
                }
                else -> handleProgress(newState.data ?: 0f, newState is State.Success)
            }
        }
    }

    private fun handleProgress(progress: Float, isAllFetchedAndCached: Boolean) {
        _state.update {
            it.copy(
                progress = progress,
                isGoToHomeScreen = isAllFetchedAndCached,
                isDataFetchInProgress = isAllFetchedAndCached.not()
            )
        }
    }

    private fun handleError(progress: State.Error<Float>) {
        _state.update {
            it.copy(
                isProgressVisible = false,
                infoBannerData = progress.getInfoBanner(),
                isDataFetchInProgress = false
            )
        }
    }
}
