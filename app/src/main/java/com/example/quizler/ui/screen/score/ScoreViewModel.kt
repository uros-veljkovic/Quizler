package com.example.quizler.ui.screen.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.State
import com.example.domain.usecase.IGetScoresUseCase
import com.example.quizler.R
import com.example.quizler.model.ChosableItem
import com.example.quizler.model.InfoBannerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreViewModel(
    choosableModeItemsProvider: ChoosableModeItemsProvider,
    private val getScoresUseCase: IGetScoresUseCase,
    private val scoreMapper: ScoresUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(ScoreScreenState())
    val state = combine(
        _state,
        choosableModeItemsProvider(),
        getScoresUseCase(),
    ) { state, modes, scores ->
        state.copy(
            scores = scoreMapper.map(scores),
            modes = modes,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ScoreScreenState()
    )

    fun setChosenMode(item: ChosableItem.Content) {
        _state.update { it.copy(chosenMode = item) }
    }

    fun refreshScoreboard() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)
            val result = getScoresUseCase.fetchAndCache(isForceRefresh = true)
            val infoBanner = if (result is State.Error)
                InfoBannerData.NoNetwork
            else
                InfoBannerData.DataRefreshed(R.string.score_refresh_title)

            _state.update { it.copy(infoBannerData = infoBanner, isLoading = false) }
            delay(DELAY_BEFORE_ERROR_DISAPPEAR)
            _state.update { it.copy(infoBannerData = null) }
        }
    }

    fun setIsDropdownExpanded(isExpanded: Boolean) {
        _state.update { it.copy(isDropdownExpanded = isExpanded) }
    }

    companion object {
        const val DELAY_BEFORE_ERROR_DISAPPEAR = 5000L
    }
}
