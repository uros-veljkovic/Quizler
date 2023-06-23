package com.example.quizler.utils.signin.manager.token.refresh

class FacebookRefreshTokenStrategy : IRefreshTokenStrategy {
    override suspend fun refreshToken(token: String): Boolean {
        TODO("Not yet implemented")
    }
    companion object {
        const val NAME = "GoogleRefreshTokenStrategy"
    }
}
