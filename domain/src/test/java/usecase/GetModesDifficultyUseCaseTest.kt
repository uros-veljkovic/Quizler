package usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.local.entity.DifficultyModeEntity
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.DifficultyModeDto
import com.example.data.remote.dto.DifficultyModesDto
import com.example.domain.State
import com.example.domain.mapper.DifficultyModeDtoMapper
import com.example.domain.mapper.DifficultyModeMapper
import com.example.domain.network.FetchAndCacheManager
import com.example.domain.network.IFetchAndCacheManager
import com.example.domain.network.inspector.NetworkInspector
import com.example.domain.usecase.GetModesDifficultyUseCase
import com.example.util.data.RepositoryResponse
import com.example.util.exceptions.ConnectivityException
import com.example.util.exceptions.ServerUnavailableException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class GetModesDifficultyUseCaseTest {

    private val networkInspector: NetworkInspector = mockk()
    private val fetchAndCacheManager: IFetchAndCacheManager =
        FetchAndCacheManager(UnconfinedTestDispatcher(), networkInspector, mockk())
    private val remoteRepository: IQuizRemoteRepository = mockk()
    private val localRepository: IQuizLocalRepository = mockk()
    private val mapper: DifficultyModeDtoMapper = DifficultyModeDtoMapper()
    private val domainMapper: DifficultyModeMapper = DifficultyModeMapper()
    private lateinit var getModesDifficultyUseCase: GetModesDifficultyUseCase

    @Before
    fun setUp() {
        getModesDifficultyUseCase = GetModesDifficultyUseCase(
            fetchAndCacheManager,
            remoteRepository,
            localRepository,
            mapper,
            domainMapper
        )
    }

    @Test
    fun `test fetch and cache success`() = runBlocking {
        // Given
        val localData = emptyList<DifficultyModeEntity>()
        val remoteData = DifficultyModesDto(
            id = "dfsa232324324", name = "difficulty",
            submodes = listOf(
                DifficultyModeDto(
                    id = "2jkljl",
                    parentModeId = "jdflkaj",
                    name = "hard",
                    numberOfHints = 1,
                    numberOfQuestions = 20,
                    timePerQuestion = 15
                )
            )
        )
        coEvery { localRepository.readDifficultyModes() } returns flowOf(localData)
        coEvery { remoteRepository.getDifficultyModes() } returns RepositoryResponse.Success(remoteData)
        coEvery { localRepository.insertDifficultyModes(any()) } returns Unit

        // When
        val result = getModesDifficultyUseCase.fetchAndCache()

        // Then
        assertEquals(State.Success::class, result::class)
    }

    @Test
    fun `test fetch and cache server error`() = runBlocking {
        // Given
        val localData = emptyList<DifficultyModeEntity>()
        val throwable = Throwable("Server error")
        coEvery { localRepository.readDifficultyModes() } returns flowOf(localData)
        coEvery { remoteRepository.getDifficultyModes() } returns RepositoryResponse.Failure(throwable)

        // When
        val result = getModesDifficultyUseCase.fetchAndCache()

        // Then
        assertEquals(State.Error::class, result::class)
        assertEquals(ServerUnavailableException::class, result.error!!::class)
    }

    @Test
    fun `test fetch and cache connectivity error`() = runBlocking {
        // Given
        val localData = emptyList<DifficultyModeEntity>()
        coEvery { localRepository.readDifficultyModes() } returns flowOf(localData)
        coEvery { remoteRepository.getDifficultyModes() } throws ConnectException()

        // When
        val result = getModesDifficultyUseCase.fetchAndCache()

        // Then
        assertEquals(State.Error::class, result::class)
        assertEquals(ConnectivityException::class, result.error!!::class)
    }

    @Test
    fun `test fetch and cache general exception`() = runBlocking {
        val localData = emptyList<DifficultyModeEntity>()
        val exception = Exception("General exception")
        coEvery { localRepository.readDifficultyModes() } returns flowOf(localData)
        coEvery { remoteRepository.getDifficultyModes() } throws exception

        val result = getModesDifficultyUseCase.fetchAndCache()

        coVerify(exactly = 0) { localRepository.insertDifficultyModes(any()) }
        assertEquals(State.Error::class, result::class)
        assertEquals(exception.message, result.error!!.message)
    }

    @Test
    fun `test no fetch due to existing data`() = runBlocking {
        val localData = listOf(
            DifficultyModeEntity(
                id = "321h41hj4jk132h",
                name = "hard",
                numberOfHints = 2,
                numberOfQuestions = 20,
                timePerQuestion = 20
            )
        )
        coEvery { localRepository.readDifficultyModes() } returns flowOf(localData)

        val result = getModesDifficultyUseCase.fetchAndCache()

        coVerify(exactly = 0) { remoteRepository.getDifficultyModes() }
        coVerify(exactly = 0) { localRepository.insertDifficultyModes(any()) }
        assertEquals(State.Success::class, result::class)
    }

    // Test cases will be added below
}
