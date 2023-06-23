package com.example.quizler.utils.signin.manager.token.refresh

import com.example.domain.usecase.IGetTokenWithProviderUseCase

class RefreshTokenFacade(
    private val googleRefreshTokenStrategy: IRefreshTokenStrategy,
    private val facebookRefreshTokenStrategy: IRefreshTokenStrategy,
    private val getTokenWithProviderUseCase: IGetTokenWithProviderUseCase
) : IRefreshTokenFacade {

    override suspend fun refreshToken(): Boolean {
        val tokenBundle = getTokenWithProviderUseCase()
        return when (tokenBundle.tokenProvider) {
            AuthenticationProviders.GOOGLE -> googleRefreshTokenStrategy.refreshToken(tokenBundle.token ?: "")
            AuthenticationProviders.FACEBOOK -> facebookRefreshTokenStrategy.refreshToken(tokenBundle.token ?: "")
            else -> false
        }
    }
}
