package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.local.entity.UserProfileEntity
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.user.UserDto
import com.example.domain.State
import com.example.domain.model.UserProfile
import com.example.domain.network.FetchAndCacheManager
import com.example.util.data.RepositoryResponse
import com.example.util.mapper.DataMapper

interface IGetUserProfileUseCase {
    suspend operator fun invoke(userId: String): State<UserProfile>
}

class GetUserProfileUseCase(
    private val fetchAndCacheManager: FetchAndCacheManager,
    private val localRepository: IQuizLocalRepository,
    private val remoteRepository: IQuizRemoteRepository,
    private val dtoMapper: DataMapper<UserDto, UserProfileEntity>,
    private val domainMapper: DataMapper<UserProfileEntity, UserProfile>,
) : IGetUserProfileUseCase {

    override suspend operator fun invoke(userId: String): State<UserProfile> {
        return when (val result = remoteRepository.getUser(userId)) {
            is RepositoryResponse.Failure -> State.Error(result.throwable)
            is RepositoryResponse.Success -> State.Success(UserProfile("", ""))
        }
    }
}
