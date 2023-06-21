package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.local.entity.UserProfileEntity
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.model.UserProfile
import com.example.domain.network.FetchAndCacheManager
import com.example.util.mapper.DataMapper

interface IGetUserProfileUseCase : IFetchAndCacheUseCase<String, UserProfile>

class GetUserProfileUseCase(
    private val fetchAndCacheManager: FetchAndCacheManager,
    private val localRepository: IQuizLocalRepository,
    private val remoteRepository: IQuizRemoteRepository,
    private val domainMapper: DataMapper<UserProfileEntity, UserProfile>,
) : IGetUserProfileUseCase {

    override suspend fun fetchAndCache(isForceRefresh: Boolean, input: String?): State<out UserProfile> {
        if (input == null) throw IllegalArgumentException("User profile ID should not be null or empty!")
        return fetchAndCacheManager(
            query = { localRepository.readUser() },
            shouldFetch = { isForceRefresh || it.userId.isNotEmpty() },
            fetch = { remoteRepository.getUser(input) },
            cache = { },
            mapToDomainModel = { domainMapper.map(it) }
        )
    }
}
