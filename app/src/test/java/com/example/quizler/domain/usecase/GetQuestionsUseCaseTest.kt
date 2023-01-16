package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.domain.date.IDateManager
import com.example.quizler.domain.preferences.IDataSyncCoordinator
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.NetworkActionHandler
import com.example.quizler.util.mapper.DataMapper
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class GetQuestionsUseCaseTest {

    private lateinit var sut: GetQuestionsUseCase
    private lateinit var netRepo: INetworkRepository
    private lateinit var networkActionHandler: INetworkActionHandler
    private lateinit var remoteRepository: IQuizRemoteRepository
    private lateinit var localRepository: IQuizLocalRepository
    private lateinit var dataSyncCoordinator: IDataSyncCoordinator
    private lateinit var dateManager: IDateManager
    private lateinit var dtoMapper: DataMapper<QuestionDto, QuestionWithAnswersEntity>
    private lateinit var uiMapper: DataMapper<QuestionWithAnswersEntity, QuestionBundle>

    @Before
    fun setUp() {
        netRepo = mockk()
        networkActionHandler = NetworkActionHandler(UnconfinedTestDispatcher(), netRepo)
        remoteRepository = mockk()
        localRepository = mockk()
        dataSyncCoordinator = mockk()
        dateManager = mockk()
        dtoMapper = mockk()
        uiMapper = mockk()

        sut = GetQuestionsUseCase(
            networkActionHandler,
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
        every { netRepo.getHasInternetConnection() } returns true

        coEvery { dataSyncCoordinator.getDataSyncTime() } returns flowOf(0)
        coEvery { dateManager.hasPast(any(), any()) } returns true

        every { localRepository.readQuestionsWithAnswers() } returns flowOf(mockk())
        coEvery { remoteRepository.getQuestions() } returns RepositoryResponse.Success(mockk())
        coEvery { dataSyncCoordinator.setDataSyncTime(any()) } just Runs
        every { dtoMapper.map(any<List<QuestionDto>>()) } returns emptyList()
        coEvery { localRepository.insertQuestionsWithAnswers(any()) } just Runs

        sut.fetchAndCache()

        verify(exactly = 2) { localRepository.readQuestionsWithAnswers() }
        coVerify(exactly = 1) { remoteRepository.getQuestions() }
        coVerify(exactly = 1) { dataSyncCoordinator.setDataSyncTime(any()) }
        coVerify(exactly = 1) { localRepository.insertQuestionsWithAnswers(any()) }
    }

    @Test
    fun given30DidNotPast_whenFetchAndCacheData_thenDataIsNotFetched() = runTest {
        every { netRepo.getHasInternetConnection() } returns true

        coEvery { dataSyncCoordinator.getDataSyncTime() } returns flowOf(0)
        coEvery { dateManager.hasPast(any(), any()) } returns false

        every { localRepository.readQuestionsWithAnswers() } returns flowOf(mockk())

        sut.fetchAndCache()

        verify(exactly = 1) { localRepository.readQuestionsWithAnswers() }
        coVerify(exactly = 0) { remoteRepository.getQuestions() }
        coVerify(exactly = 0) { dataSyncCoordinator.setDataSyncTime(any()) }
        coVerify(exactly = 0) { localRepository.insertQuestionsWithAnswers(any()) }
    }
}
