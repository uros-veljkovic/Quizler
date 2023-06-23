package usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.preferences.IDataSyncCoordinator
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.date.IDateManager
import com.example.domain.mapper.QuestionWithAnswersDtoMapper
import com.example.domain.mapper.QuestionWithAnswersUiMapper
import com.example.domain.network.IFetchAndCacheManager
import com.example.domain.usecase.GetQuestionsUseCase
import com.example.util.data.RepositoryResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class GetQuestionsUseCaseTest {

    private lateinit var sut: GetQuestionsUseCase
    private lateinit var fetchAndCacheManager: IFetchAndCacheManager
    private lateinit var remoteRepository: IQuizRemoteRepository
    private lateinit var localRepository: IQuizLocalRepository
    private lateinit var dataSyncCoordinator: IDataSyncCoordinator
    private lateinit var dateManager: IDateManager
    private lateinit var dtoMapper: QuestionWithAnswersDtoMapper
    private lateinit var uiMapper: QuestionWithAnswersUiMapper

    @Before
    fun setUp() {
        fetchAndCacheManager = mockk()
        remoteRepository = mockk()
        localRepository = mockk()
        dataSyncCoordinator = mockk()
        dateManager = mockk()
        dtoMapper = mockk()
        uiMapper = mockk()

        sut = GetQuestionsUseCase(
            fetchAndCacheManager,
            remoteRepository,
            localRepository,
            dataSyncCoordinator,
            dateManager,
            dtoMapper,
            uiMapper
        )
    }

    @Test
    fun given30DaysHasPast_whenFetchAndCacheData_thenDataIsFetchedAndCached() = runTest {
        coEvery { dataSyncCoordinator.getDataSyncTime() } returns flowOf(0)
        coEvery { dateManager.hasDaysPassed(any(), any()) } returns true

        every { localRepository.readQuestionsWithAnswers() } returns flowOf(mockk())
        coEvery { remoteRepository.getQuestions() } returns RepositoryResponse.Success(mockk())
        coEvery { dataSyncCoordinator.setDataSyncTime(any()) } just Runs
        every { dtoMapper.map(any<List<com.example.data.remote.dto.QuestionDto>>()) } returns emptyList()
        coEvery { localRepository.insertQuestionsWithAnswers(any()) } just Runs

        sut.fetchAndCache()

        verify(exactly = 2) { localRepository.readQuestionsWithAnswers() }
        coVerify(exactly = 1) { remoteRepository.getQuestions() }
        coVerify(exactly = 1) { dataSyncCoordinator.setDataSyncTime(any()) }
        coVerify(exactly = 1) { localRepository.insertQuestionsWithAnswers(any()) }
    }

    @Test
    fun given30DidNotPast_whenFetchAndCacheData_thenDataIsNotFetched() = runTest {
        coEvery { dataSyncCoordinator.getDataSyncTime() } returns flowOf(0)
        coEvery { dateManager.hasDaysPassed(any(), any()) } returns false

        every { localRepository.readQuestionsWithAnswers() } returns flowOf(mockk())

        sut.fetchAndCache()

        verify(exactly = 1) { localRepository.readQuestionsWithAnswers() }
        coVerify(exactly = 0) { remoteRepository.getQuestions() }
        coVerify(exactly = 0) { dataSyncCoordinator.setDataSyncTime(any()) }
        coVerify(exactly = 0) { localRepository.insertQuestionsWithAnswers(any()) }
    }
}
