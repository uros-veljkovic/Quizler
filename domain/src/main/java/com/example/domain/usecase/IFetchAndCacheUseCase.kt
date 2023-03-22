package com.example.domain.usecase

import com.example.domain.State

interface IFetchAndCacheUseCase {
    suspend fun fetchAndCache(isForceRefresh: Boolean = false): State<Unit>
}
