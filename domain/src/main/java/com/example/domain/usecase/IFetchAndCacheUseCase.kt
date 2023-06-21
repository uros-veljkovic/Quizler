package com.example.domain.usecase

import com.example.domain.State

interface IFetchAndCacheUseCase<in Input, out Output> {
    suspend fun fetchAndCache(isForceRefresh: Boolean = false, input: Input? = null): State<out Output>
}
