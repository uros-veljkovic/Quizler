package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.user.UserDto
import com.example.domain.State
import com.example.domain.model.UserProfile
import com.example.domain.network.FetchAndCacheManager

interface IGetUserProfileUseCase : IFetchAndCacheUseCase {
    suspend operator fun invoke(): UserProfile
}

class GetUserProfileUseCase(
    private val fetchAndCacheManager: FetchAndCacheManager,
    private val localRepository: IQuizLocalRepository,
    private val remoteRepository: IQuizRemoteRepository
) : IGetUserProfileUseCase {

    override suspend fun invoke(): UserProfile {
        return UserProfile("", "")
    }

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return fetchAndCacheManager(
            shouldFetch = { it == null },
            query = { localRepository.readUser() },
            fetch = { remoteRepository.getUser("") },
            cache = { userDto: UserDto? ->
                // TODO: Think about refactoring IFetchAndCacheUseCase to handle input and return output
            }
        )
    }
}
