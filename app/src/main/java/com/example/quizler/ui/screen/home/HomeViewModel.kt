package com.example.quizler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.domain.usecase.GetModesCategoryUseCase
import com.example.quizler.domain.usecase.GetModesDifficultyUseCase
import com.example.quizler.domain.usecase.GetModesLengthUseCase
import com.example.quizler.util.mapper.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getModesCategory: GetModesCategoryUseCase,
    private val getModesLength: GetModesLengthUseCase,
    private val getModesDifficulty: GetModesDifficultyUseCase,
    private val mapper: DataMapper<BaseQuizModeEntity, QuizMode>,
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
