package com.example.domain.usecase

import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.model.UserProfile

interface IUpdateUserProfileUseCase {
    suspend operator fun invoke(profile: UserProfile)
}

class UpdateProfileInfoUseCase(
    private val remoteRepository: IQuizRemoteRepository
) : IUpdateUserProfileUseCase {
    override suspend fun invoke(profile: UserProfile) {
        TODO("Not yet implemented")
    }
}
