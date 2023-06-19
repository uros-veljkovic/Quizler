package usecase

import com.example.data.preferences.IAppPreferences
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.SignInResponseDto
import com.example.domain.State
import com.example.domain.usecase.ISignInUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.util.data.RepositoryResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import util.FakeAppPreferences

class SignInUseCaseTest {

    private lateinit var sut: ISignInUseCase
    private lateinit var remoteRepository: IQuizRemoteRepository
    private lateinit var preferences: IAppPreferences

    @Before
    fun setUp() {
        remoteRepository = mockk()
        preferences = FakeAppPreferences()
        sut = SignInUseCase(remoteRepository, preferences)
    }

    @Test
    fun `invoke returns success with true when sign in succeeds and token gets stored and response contains true`(): Unit =
        runTest {
            val expectedToken = "token"
            val expectedIsProfileFullyCreated = true

            coEvery { remoteRepository.signIn(expectedToken) } returns RepositoryResponse.Success(
                SignInResponseDto(
                    true
                )
            )

            val state = sut(expectedToken)

            coVerify(exactly = 1) { remoteRepository.signIn(expectedToken) }
            assertEquals(expectedIsProfileFullyCreated, state.data)
            assertEquals(State.Success::class, state::class)
        }

    @Test
    fun `invoke returns success with false when sign in succeeds and token gets stored and response contains false`(): Unit =
        runTest {
            val expectedToken = "token"
            val expectedIsProfileFullyCreated = false

            coEvery { remoteRepository.signIn(expectedToken) } returns RepositoryResponse.Success(
                SignInResponseDto(
                    false
                )
            )

            val state = sut(expectedToken)

            coVerify(exactly = 1) { remoteRepository.signIn(expectedToken) }
            assertEquals(expectedIsProfileFullyCreated, state.data)
            assertEquals(State.Success::class, state::class)
        }

    @Test
    fun `invoke returns error with sign in fails`(): Unit =
        runTest {
            val expectedToken = "token"

            coEvery { remoteRepository.signIn(expectedToken) } returns RepositoryResponse.Failure(Exception())

            val state = sut(expectedToken)

            coVerify(exactly = 1) { remoteRepository.signIn(expectedToken) }
            assertEquals(null, state.data)
            assertEquals(State.Error::class, state::class)
        }
}
