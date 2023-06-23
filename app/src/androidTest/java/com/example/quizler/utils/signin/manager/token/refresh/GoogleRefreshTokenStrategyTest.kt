package com.example.quizler.utils.signin.manager.token.refresh

import android.content.Context
import com.example.domain.usecase.ICacheTokenUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GoogleRefreshTokenStrategyTest {

    private val context: Context = mockk()
    private val cacheTokenUseCase: ICacheTokenUseCase = mockk()
    private val googleSignInClient: GoogleSignInClient = mockk()
    private val account: GoogleSignInAccount = mockk()

    private lateinit var strategy: GoogleRefreshTokenStrategy

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        mockkStatic(GoogleSignIn::getClient)
        every { GoogleSignIn.getClient(any(), any()) } returns googleSignInClient
        strategy = GoogleRefreshTokenStrategy(context, cacheTokenUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun refresh_token_should_return_true_when_silent_sign_in_is_successful_and_id_token_is_not_null() = runBlocking {
        coEvery { googleSignInClient.silentSignIn().await() } returns account
        every { account.idToken } returns "idToken"
        coEvery { cacheTokenUseCase(any(), any()) } returns true

        val result = strategy.refreshToken("token")

        assert(result)
        coVerify { cacheTokenUseCase("idToken", AuthenticationProviders.GOOGLE) }
    }

    @Test
    fun refreshToken_should_return_false_when_silentSignIn_is_successful_but_idToken_is_null() = runBlocking {
        coEvery { googleSignInClient.silentSignIn().await() } returns account
        every { account.idToken } returns null

        val result = strategy.refreshToken("token")

        assert(!result)
        coVerify(exactly = 0) { cacheTokenUseCase(any(), any()) }
    }

    @Test
    fun refreshToken_should_return_false_when_silentSignIn_throws_an_exception() = runBlocking {
        coEvery { googleSignInClient.silentSignIn().await() } throws Exception()

        val result = strategy.refreshToken("token")

        assert(!result)
        coVerify(exactly = 0) { cacheTokenUseCase(any(), any()) }
    }
}
