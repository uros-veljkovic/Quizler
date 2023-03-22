package com.example.quizler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.IGetModesCategoryUseCase
import com.example.domain.usecase.IGetModesDifficultyUseCase
import com.example.domain.usecase.IGetModesLengthUseCase
import com.example.quizler.ui.screen.home.mapper.QuizModeMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getModesCategory: IGetModesCategoryUseCase,
    private val getModesLength: IGetModesLengthUseCase,
    private val getModesDifficulty: IGetModesDifficultyUseCase,
    private val mapper: QuizModeMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            launch {
                observeCategoryModes()
            }
            launch {
                observeDifficultyModes()
            }
            launch {
                observeLengthModes()
            }
        }
    }

    private suspend fun observeDifficultyModes() {
        getModesDifficulty().catch {
            _state.update { it.copyHasError(true) }
        }.collect { newState ->
            _state.update { it.copyWithDifficultyModes(mapper.map(newState)) }
        }
    }

    private suspend fun observeCategoryModes() {
        getModesCategory().catch {
            _state.update { it.copyHasError(true) }
        }.collect { newState ->
            _state.update { it.copyWithCategoryModes(mapper.map(newState)) }
        }
    }

    private suspend fun observeLengthModes() {
        getModesLength().catch {
            _state.update { it.copyHasError(true) }
        }.collect { newState ->
            _state.update { it.copyWithLengthModes(mapper.map(newState)) }
        }
    }
}
