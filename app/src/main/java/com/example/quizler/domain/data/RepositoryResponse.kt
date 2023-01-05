package com.example.quizler.domain.data

sealed class RepositoryResponse<T> {

    class Success<U>(val data: U) : RepositoryResponse<U>()
    class Failure<V>(val throwable: Throwable) : RepositoryResponse<V>()
}
