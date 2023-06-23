package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.preferences.IAppPreferences
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.UserProfileDtoMapper
import com.example.util.data.RepositoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ISignInUseCase {
    /**
     * Sign in a user using token
     *
     * @param token jwt token
     * @param tokenProvider jwt token provider (Google or Facebook)
     * @return [State.Success] with true if is fully created (username and avatar populated in database)
     * [State.Success] if sign in is successfull
     * [State.Error] if newtork call fails
     */
    suspend operator fun invoke(token: String, tokenProvider: String): State<Unit>
}

class SignInUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val appPreferences: IAppPreferences,
    private val mapper: UserProfileDtoMapper
) : ISignInUseCase {

    override suspend fun invoke(token: String, tokenProvider: String): State<Unit> = withContext(Dispatchers.IO) {
        appPreferences.setToken(token)
        appPreferences.setTokenProvider(tokenProvider)
        when (val result = remoteRepository.signIn()) {
            is RepositoryResponse.Failure -> {
                appPreferences.clearToken()
                appPreferences.clearTokenProvider()
                State.Error(result.throwable)
            }

            is RepositoryResponse.Success -> {
                val userData = result.data
                localRepository.insertUser(mapper.map(userData))
                State.Success(Unit)
            }
        }
    }
}
