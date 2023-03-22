package com.example.domain.usecase

import com.example.data.local.INetworkRepository

interface ISetHasInternetConnectionUseCase {
    suspend operator fun invoke(hasConnection: Boolean)
}

class SetHasInternetConnectionUseCase(
    private val repository: INetworkRepository
) : ISetHasInternetConnectionUseCase {
    override suspend fun invoke(hasConnection: Boolean) = repository.setHasInternetConnection(hasConnection)
}
