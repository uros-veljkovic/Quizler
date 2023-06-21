package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.local.entity.UserProfileEntity
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.user.UserDto
import com.example.domain.State
import com.example.domain.model.UserProfile
import com.example.domain.network.FetchAndCacheManager
import com.example.util.mapper.DataMapper

interface IGetCurrentUserProfileUseCase : IFetchAndCacheUseCase<Unit, UserProfile>

class GetCurrentUserProfileUseCase(
    private val fetchAndCacheManager: FetchAndCacheManager,
    private val localRepository: IQuizLocalRepository,
    private val remoteRepository: IQuizRemoteRepository,
    private val dtoMapper: DataMapper<UserDto, UserProfileEntity>,
    private val domainMapper: DataMapper<UserProfileEntity, UserProfile>,
) : IGetCurrentUserProfileUseCase {

    override suspend fun fetchAndCache(isForceRefresh: Boolean, input: Unit?): State<out UserProfile> {
        return fetchAndCacheManager(
            query = { localRepository.readUser() },
            shouldFetch = { isForceRefresh || it.userId.isNotEmpty() },
            fetch = { remoteRepository.getCurrentUser() },
            cache = { localRepository.insertUser(dtoMapper.map(it)) },
            mapToDomainModel = { domainMapper.map(it) }
        )
    }
}
