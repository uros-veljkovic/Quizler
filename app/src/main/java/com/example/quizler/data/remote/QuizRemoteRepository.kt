package com.example.quizler.data.remote

import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.data.remote.dto.ScoreDto
import com.example.quizler.data.remote.service.quizmode.QuizService
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.INetworkActionHandler
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QRealRemoteRepo

class QuizRemoteRepository(
    private val service: QuizService,
    private val networkActionHandler: INetworkActionHandler
) : IQuizRemoteRepository {

    override suspend fun getCategoryModes() = networkActionHandler.executeNetworkAction {
        service.getCategoryModes()
    }

    override suspend fun getLengthModes() = networkActionHandler.executeNetworkAction {
        service.getLengthModes()
    }

    override suspend fun getDifficultyModes() = networkActionHandler.executeNetworkAction {
        service.getDifficultyModes()
    }

    override suspend fun getQuestions(approvedQuestions: Boolean) =
        networkActionHandler.executeNetworkAction {
            service.getQuestions(approvedQuestions)
        }

    override suspend fun getScores(): RepositoryResponse<List<ScoreDto>> =
        networkActionHandler.executeNetworkAction {
            service.getScores()
        }

    override suspend fun record(answerRecordDto: AnswerRecordDto) =
        networkActionHandler.executeNetworkAction {
            service.record(answerRecordDto)
        }

    override suspend fun record(resultRecordDto: ResultRecordDto) =
        networkActionHandler.executeNetworkAction {
            service.record(resultRecordDto)
        }

    override suspend fun report(questionId: String) = networkActionHandler.executeNetworkAction {
        service.report(questionId)
    }
}
