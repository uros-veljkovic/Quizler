package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.UserProfileDomainMapper
import com.example.domain.mapper.UserProfileDtoMapper
import com.example.domain.mapper.UserProfileRequestMapper
import com.example.domain.model.UserProfile
import com.example.domain.network.IFetchAndCacheManager

interface IUpdateCurrentUserProfileUseCase : IFetchAndCacheUseCase<UserProfile, UserProfile>

class UpdateCurrentUserProfileUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val localRepository: IQuizLocalRepository,
    private val remoteRepository: IQuizRemoteRepository,
    private val dtoToEntityMapper: UserProfileDtoMapper,
    private val requestMapper: UserProfileRequestMapper,
    private val domainMapper: UserProfileDomainMapper,
) : IUpdateCurrentUserProfileUseCase {

    override suspend fun fetchAndCache(isForceRefresh: Boolean, input: UserProfile?): State<out UserProfile> {
        if (input == null) throw IllegalArgumentException("Input representing userId should not be empty!")

        return fetchAndCacheManager(
            query = { localRepository.readUser() },
            shouldFetch = { true },
            fetch = { remoteRepository.updateCurrentUser(requestMapper.map(input)) },
            cache = { localRepository.insertUser(dtoToEntityMapper.map(it)) },
            mapToDomainModel = { domainMapper.map(it) }
        )
    }
}
