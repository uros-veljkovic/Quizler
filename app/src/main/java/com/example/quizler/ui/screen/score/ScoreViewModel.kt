package com.example.quizler.ui.screen.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.usecase.GetModeDropdownItemsUseCase
import com.example.quizler.domain.usecase.GetScoresUseCase
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.ui.model.Score
import com.example.quizler.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val getModesUseCase: GetModeDropdownItemsUseCase,
    private val getScoresUseCase: GetScoresUseCase,
    private val repository: INetworkRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScoreScreenState())
    val state = _state.asStateFlow()

    private val scores = mutableListOf<Score>()

    init {
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            launch {
                observeModes()
            }
            launch {
                observeScores()
            }
            launch {
                observeNetwork()
            }
        }
    }

    private suspend fun observeScores() {
        getScoresUseCase().collect { scores: List<Score> ->
            this.scores.clear()
            this.scores.addAll(scores)
            _state.update { it.copyWithScores(scores) }
        }
    }

    private suspend fun observeModes() {
        getModesUseCase().catch {
            _state.update { it.copyWithError() }
        }.collect { state ->
            _state.update { it.copyWithModes(state) }
        }
    }

    private suspend fun observeNetwork() {
        repository.getHasInternetConnectionFlow().collect {
            it?.let { hasConnection ->
                _state.update { it.copyHasConnection(hasConnection) }
            }
        }
    }

    fun setChosenMode(item: DropdownItem.Content) {
        _state.update { it.copyWithChosenMode(item, scores) }
    }

    fun refreshScoreboard() {
        viewModelScope.launch {
            _state.update { it.copyWithLoading(true) }
            val result = getScoresUseCase.fetchAndCache(isForceRefresh = true)
            if (result is State.Error) {
                _state.update { it.copyWithError() }
                delay(DELAY_BEFORE_ERROR_DISAPPEAR)
                _state.update { it.copyWithNoError() }
            } else {
                _state.update { it.copyWithLoading(false) }
            }
        }
    }

    fun setIsDropdownExpanded(isExpanded: Boolean) {
        _state.update { it.copyWithDropdownExpandedStateChange(isExpanded = isExpanded) }
    }

    companion object {
        const val DELAY_BEFORE_ERROR_DISAPPEAR = 5000L
    }
}
