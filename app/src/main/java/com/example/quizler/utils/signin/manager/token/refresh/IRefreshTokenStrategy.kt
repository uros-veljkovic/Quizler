package com.example.quizler.utils.signin.manager.token.refresh

interface IRefreshTokenStrategy {
    /**
     * Refreshes a token and returns true if token refreshed successfully
     * @param token token to be refreshed
     * @return
     */
    suspend fun refreshToken(token: String): Boolean
}
