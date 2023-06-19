package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import com.example.data.preferences.IUserPreferences
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.user.UserDto
import com.example.domain.State
import com.example.util.data.RepositoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ISignInUseCase {
    /**
     * Sign in a user using token
     *
     * @param token jwt token
     * @return [State.Success] with true if is fully created (username and avatar populated in database)
     * [State.Success] if sign in is successfull
     * [State.Error] if newtork call fails
     */
    suspend operator fun invoke(token: String): State<Unit>
}

class SignInUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val appPreferences: IAppPreferences,
    private val userPreferences: IUserPreferences,
) : ISignInUseCase {

    override suspend fun invoke(token: String): State<Unit> = withContext(Dispatchers.IO) {
        appPreferences.setToken(token)
        when (val result = remoteRepository.signIn()) {
            is RepositoryResponse.Failure -> {
                appPreferences.clearToken()
                State.Error(result.throwable)
            }

            is RepositoryResponse.Success -> {
                val userData = result.data
                updatePreferences(userData)
                State.Success(Unit)
            }
        }
    }

    private suspend fun updatePreferences(userData: UserDto) {
        with(userPreferences) {
            setUserId(userData.userId)
            setAvatar(userData.avatar)
            setUsername(userData.username)
            setProfileImageUrl(userData.profileImage)
        }
    }
}
