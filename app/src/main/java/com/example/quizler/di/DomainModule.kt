package com.example.quizler.di

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.example.quizler.data.local.entity.AnswerEntity
import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.local.entity.ScoreEntity
import com.example.quizler.data.remote.dto.CategoryModesDto
import com.example.quizler.data.remote.dto.DifficultyModesDto
import com.example.quizler.data.remote.dto.LengthModesDto
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.data.remote.dto.ReportTypeDto
import com.example.quizler.data.remote.dto.ScoreDto
import com.example.quizler.data.remote.dto.mapper.AnswerDtoMapper
import com.example.quizler.data.remote.dto.mapper.CategoryModeDtoMapper
import com.example.quizler.data.remote.dto.mapper.DifficultyModeDtoMapper
import com.example.quizler.data.remote.dto.mapper.LengthModeDtoMapper
import com.example.quizler.data.remote.dto.mapper.QuestionDtoMapper
import com.example.quizler.data.remote.dto.mapper.QuestionWithAnswersDtoMapper
import com.example.quizler.data.remote.dto.mapper.ReportTypeDtoMapper
import com.example.quizler.data.remote.dto.mapper.ScoreDtoMapper
import com.example.quizler.domain.QSendDataToServerWorker
import com.example.quizler.domain.SendDataToServerWorker
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.date.DateManager
import com.example.quizler.domain.date.IDateManager
import com.example.quizler.domain.usecase.GetModesCategoryUseCase
import com.example.quizler.domain.usecase.GetModesDifficultyUseCase
import com.example.quizler.domain.usecase.GetModesLengthUseCase
import com.example.quizler.domain.usecase.GetQuestionsUseCase
import com.example.quizler.domain.usecase.GetReportTypesUseCase
import com.example.quizler.domain.usecase.GetScoresUseCase
import com.example.quizler.domain.usecase.HandleStartupDataUseCase
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.NetworkActionHandler
import com.example.quizler.util.mapper.DataMapper
import com.example.quizler.util.provider.AbstractResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    /**
     * ===========================================================
     * ========================= Mapper ==========================
     * ===========================================================
     **/

    @Provides
    @Singleton
    fun provideAnswerMapper(): DataMapper<QuestionDto, List<AnswerEntity>> {
        return AnswerDtoMapper()
    }

    @Provides
    @Singleton
    fun provideQuestionMapper(): DataMapper<QuestionDto, QuestionEntity> {
        return QuestionDtoMapper()
    }

    @Provides
    @Singleton
    fun provideQuestionWithAnswerMapper(
        questionMapper: DataMapper<QuestionDto, QuestionEntity>,
        answerMapper: DataMapper<QuestionDto, List<AnswerEntity>>
    ): DataMapper<QuestionDto, QuestionWithAnswersEntity> {
        return QuestionWithAnswersDtoMapper(questionMapper, answerMapper)
    }

    @Provides
    @Singleton
    fun provideCategoryModeMapper(): DataMapper<CategoryModesDto, List<CategoryModeEntity>> {
        return CategoryModeDtoMapper()
    }

    @Provides
    @Singleton
    fun provideLengthModeMapper(): DataMapper<LengthModesDto, List<LengthModeEntity>> {
        return LengthModeDtoMapper()
    }

    @Provides
    @Singleton
    fun provideDifficultyModeMapper(): DataMapper<DifficultyModesDto, List<DifficultyModeEntity>> {
        return DifficultyModeDtoMapper()
    }

    @Provides
    @Singleton
    fun provideReportTypeDtoMapper(
        resourceProvider: AbstractResourceProvider<String>
    ): DataMapper<ReportTypeDto, ReportTypeEntity> {
        return ReportTypeDtoMapper(resourceProvider)
    }

    @Provides
    @Singleton
    fun provideScoreMapper(): DataMapper<Pair<ScoreDto, Int>, ScoreEntity> {
        return ScoreDtoMapper()
    }

    @Provides
    @Singleton
    fun provideNetworkActionHandler(
        networkRepository: INetworkRepository
    ): INetworkActionHandler {
        return NetworkActionHandler(Dispatchers.IO, networkRepository)
    }

    /**
     * ===========================================================
     * ====================== Work requests ======================
     * ===========================================================
     **/

    @QSendDataToServerWorker
    @Provides
    @Singleton
    fun provideSendStoredDataToServerWorker(): OneTimeWorkRequest {

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        return OneTimeWorkRequestBuilder<SendDataToServerWorker>().setBackoffCriteria(
            BackoffPolicy.LINEAR,
            Duration.ofMinutes(1)
        ).setConstraints(constraints).build()
    }

    /**
     * ===========================================================
     * ======================== Use Case =========================
     * ===========================================================
     **/

    @Provides
    @Singleton
    fun provideHandleStartupDataUseCase(
        getQuestionsUseCase: GetQuestionsUseCase,
        getModesDifficultyUseCase: GetModesDifficultyUseCase,
        getModesLengthUseCase: GetModesLengthUseCase,
        getModesCategoryUseCase: GetModesCategoryUseCase,
        getScoresUseCase: GetScoresUseCase,
        getReportTypesUseCase: GetReportTypesUseCase
    ): HandleStartupDataUseCase {
        return HandleStartupDataUseCase(
            listOf(
                getQuestionsUseCase,
                getModesDifficultyUseCase,
                getModesLengthUseCase,
                getModesCategoryUseCase,
                getScoresUseCase,
                getReportTypesUseCase
            )
        )
    }

    @Provides
    @Singleton
    fun provideDateManager(): IDateManager {
        return DateManager()
    }
}
