package com.example.quizler.domain.usecase

import com.example.quizler.util.State

interface IFetchAndCacheUseCase {
    suspend fun fetchAndCache(isForceRefresh: Boolean = false): State<Unit>
}
