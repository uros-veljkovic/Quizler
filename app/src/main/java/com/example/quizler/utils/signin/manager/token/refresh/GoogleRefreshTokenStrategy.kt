package com.example.quizler.utils.signin.manager.token.refresh

import android.content.Context
import com.example.domain.usecase.ICacheTokenUseCase
import com.example.quizler.utils.signin.manager.GoogleSignInOptionsProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.tasks.await

class GoogleRefreshTokenStrategy(
    private val context: Context,
    private val cacheTokenUseCase: ICacheTokenUseCase
) : IRefreshTokenStrategy {
    override suspend fun refreshToken(token: String): Boolean {
        val signInOptions = GoogleSignInOptionsProvider.getOptions()

        val googleSignInClient = GoogleSignIn.getClient(context, signInOptions)

        return try {
            val account = googleSignInClient.silentSignIn().await()
            val idToken = account?.idToken
            idToken != null && cacheTokenUseCase(idToken, AuthenticationProviders.GOOGLE)
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        const val NAME = "GoogleRefreshTokenStrategy"
    }
}
