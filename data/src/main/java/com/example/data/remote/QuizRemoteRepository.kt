package com.example.data.remote

import com.example.data.remote.actionhandler.INetworkActionHandler
import com.example.data.remote.dto.AnswerRecordDto
import com.example.data.remote.dto.CategoryModesDto
import com.example.data.remote.dto.CreateNewQuestionDto
import com.example.data.remote.dto.DifficultyModesDto
import com.example.data.remote.dto.LengthModesDto
import com.example.data.remote.dto.QuestionDto
import com.example.data.remote.dto.ReportQuestionDto
import com.example.data.remote.dto.ReportTypeDto
import com.example.data.remote.dto.ResultRecordDto
import com.example.data.remote.dto.ScoreDto
import com.example.data.remote.dto.UpdateUserProfileDto
import com.example.data.remote.dto.user.UserDto
import com.example.data.remote.dto.user.UserIdDto
import com.example.data.remote.service.quizmode.QuizService
import com.example.util.data.RepositoryResponse

class QuizRemoteRepository(
    private val service: QuizService,
    private val networkActionHandler: INetworkActionHandler
) : IQuizRemoteRepository {
    override suspend fun signIn(): RepositoryResponse<UserDto> = networkActionHandler {
        service.signIn()
    }

    override suspend fun getCurrentUser(): RepositoryResponse<UserDto> = networkActionHandler {
        service.getCurrentUser()
    }

    override suspend fun getUser(id: String): RepositoryResponse<UserDto> = networkActionHandler {
        service.getUserById(UserIdDto(id))
    }

    override suspend fun getCategoryModes(): RepositoryResponse<CategoryModesDto> = networkActionHandler {
        service.getCategoryModes()
    }

    override suspend fun getLengthModes(): RepositoryResponse<LengthModesDto> = networkActionHandler {
        service.getLengthModes()
    }

    override suspend fun getDifficultyModes(): RepositoryResponse<DifficultyModesDto> = networkActionHandler {
        service.getDifficultyModes()
    }

    override suspend fun getQuestions(): RepositoryResponse<List<QuestionDto>> = networkActionHandler {
        service.getQuestions()
    }

    override suspend fun getReportTypes(): RepositoryResponse<List<ReportTypeDto>> = networkActionHandler {
        service.getReportTypes()
    }

    override suspend fun getScores(): RepositoryResponse<List<ScoreDto>> = networkActionHandler {
        service.getScores()
    }

    override suspend fun updateCurrentUser(dto: UpdateUserProfileDto): RepositoryResponse<UserDto> = networkActionHandler {
        service.updateCurrentUser(dto)
    }

    override suspend fun record(answerRecordDto: AnswerRecordDto) = networkActionHandler {
        service.record(answerRecordDto)
    }

    override suspend fun record(resultRecordDto: ResultRecordDto) = networkActionHandler {
        service.record(resultRecordDto)
    }

    override suspend fun report(questionId: String) = networkActionHandler {
        service.report(questionId)
    }

    override suspend fun report(dto: ReportQuestionDto): RepositoryResponse<Unit> = networkActionHandler {
        service.reportQuestion(dto)
    }

    override suspend fun create(question: CreateNewQuestionDto): RepositoryResponse<Unit> = networkActionHandler {
        service.create(question)
    }
}
