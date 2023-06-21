package com.example.domain.network

import com.example.domain.State
import com.example.util.data.RepositoryResponse
import kotlinx.coroutines.flow.Flow

interface IFetchAndCacheManager {
    suspend operator fun <Domain, Entity, Dto> invoke(
        shouldFetch: (Entity) -> Boolean,
        query: () -> Flow<Entity>,
        fetch: suspend () -> RepositoryResponse<Dto>,
        cache: suspend (Dto) -> Unit,
        mapToDomainModel: (Entity) -> Domain
    ): State<Domain>
}
