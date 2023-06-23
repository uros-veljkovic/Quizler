package com.example.quizler.utils.signin.manager.token.refresh

interface IRefreshTokenFacade {
    /**
     * Refreshes a [token] through appropriate [tokenProvider], and returns true if token is refreshed, otherwise false.
     *
     * @param token token to be refreshed
     * @param tokenProvider name of the token provider which will refresh the token
     */
    suspend fun refreshToken(): Boolean
}
