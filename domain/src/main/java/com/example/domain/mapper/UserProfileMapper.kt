package com.example.domain.mapper

import com.example.data.local.entity.UserProfileEntity
import com.example.data.remote.dto.UpdateUserProfileDto
import com.example.data.remote.dto.user.UserDto
import com.example.domain.model.UserProfile
import com.example.util.mapper.DataMapper

class UserProfileDtoMapper : DataMapper<UserDto, UserProfileEntity> {
    override fun map(input: UserDto): UserProfileEntity {
        return UserProfileEntity(
            userId = input.userId,
            id = input.id,
            email = input.email,
            username = input.username,
            profileImageUrl = input.profileImage,
            avatarName = input.avatar
        )
    }
}

class UserProfileDomainMapper : DataMapper<UserProfileEntity, UserProfile> {
    override fun map(input: UserProfileEntity): UserProfile {
        return UserProfile(
            username = input.username ?: "",
            avatar = input.avatarName ?: "",
            profileImageUrl = input.profileImageUrl ?: ""
        )
    }
}

class UserProfileRequestMapper : DataMapper<UserProfile, UpdateUserProfileDto> {
    override fun map(input: UserProfile): UpdateUserProfileDto {
        return UpdateUserProfileDto(
            username = input.username,
            avatar = input.avatar,
            profileImageUrl = input.profileImageUrl
        )
    }
}
