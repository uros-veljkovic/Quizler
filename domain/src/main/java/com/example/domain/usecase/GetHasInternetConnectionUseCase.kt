package com.example.domain.usecase

import com.example.data.local.INetworkRepository
import kotlinx.coroutines.flow.StateFlow

interface IGetHasInternetConnectionUseCase {
    fun invoke(): StateFlow<Boolean?>
}

class GetHasInternetConnectionUseCase(
    private val repository: INetworkRepository
) : IGetHasInternetConnectionUseCase {
    override fun invoke() = repository.getHasInternetConnectionFlow()
}
