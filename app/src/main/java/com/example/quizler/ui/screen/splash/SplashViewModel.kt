package com.example.quizler.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.usecase.HandleStartupDataUseCase
import com.example.quizler.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val handleStartupDataUseCase: HandleStartupDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                handleStartupData()
            }
            launch {
                handleNetworkConnectivity()
            }
        }
    }

    private suspend fun handleNetworkConnectivity() {
        networkRepository.getHasInternetConnectionFlow().collect { hasConnection ->
            hasConnection?.let {
                _state.update { it.copyWithChangedNetworkConnectivity(hasConnection) }
            }
        }
    }

    private suspend fun handleStartupData() {
        handleStartupDataUseCase().collect { result ->
            when (result) {
                is State.Error -> handleError(result)
                is State.Success -> handleSuccess(result)
                else -> {
                    // do nothing
                }
            }
        }
    }

    private suspend fun handleSuccess(progress: State.Success<Float>) = withContext(Dispatchers.Main) {
        progress.data?.let {
            _state.update { it.copyWithProgress(progress.data) }
        }
    }

    private fun handleError(progress: State.Error<Float>) {
        _state.update { it.copyWithError(progress) }
    }
}
