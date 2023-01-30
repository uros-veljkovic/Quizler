package com.example.quizler.ui.screen.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.R
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.usecase.GetModeDropdownItemsUseCase
import com.example.quizler.domain.usecase.GetScoresUseCase
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    getModesUseCase: GetModeDropdownItemsUseCase,
    private val getScoresUseCase: GetScoresUseCase,
    repository: INetworkRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScoreScreenState())
    val state = combine(
        _state,
        getModesUseCase(),
        getScoresUseCase(),
        repository.getHasInternetConnectionFlow()
    ) { state, modes, scores, connection ->
        state.copy(
            scores = scores,
            modes = modes,
            hasNetwork = connection ?: true,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ScoreScreenState()
    )

    fun setChosenMode(item: DropdownItem.Content) {
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
