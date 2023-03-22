package com.example.data.remote.actionhandler

import com.example.util.data.RepositoryResponse
import retrofit2.Response

interface INetworkActionHandler {
    suspend operator fun <T> invoke(load: suspend () -> Response<T>): RepositoryResponse<T>
}
