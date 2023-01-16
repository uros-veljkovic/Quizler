package com.example.quizler.domain.usecase

import com.example.quizler.util.State

interface IFetchAndCacheUseCase {
    suspend fun fetchAndCache(): State<Unit>
}
