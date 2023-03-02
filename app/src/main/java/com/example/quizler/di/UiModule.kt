package com.example.quizler.di

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.local.entity.BaseQuizModeEntity
import com.example.quizler.data.local.entity.InvalidQuestionReportEntity
import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.local.entity.ScoreEntity
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.data.remote.dto.mapper.AnswerRecordDtoMapper
import com.example.quizler.data.remote.dto.mapper.ResultRecordDtoMapper
import com.example.quizler.domain.mapper.QuizModeDropdownItemMapper
import com.example.quizler.domain.mapper.ReportedQuestionMapper
import com.example.quizler.domain.mapper.ScoresUiMapper
import com.example.quizler.domain.model.Answer
import com.example.quizler.domain.model.InvalidQuestionReport
import com.example.quizler.domain.model.Question
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.domain.usecase.GetQuestionsUseCase
import com.example.quizler.domain.usecase.GetReportTypesUseCase
import com.example.quizler.domain.usecase.GetUsernameUseCase
import com.example.quizler.domain.usecase.SendInvalidQuestionReportUseCase
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.ui.model.ReportType
import com.example.quizler.ui.model.Score
import com.example.quizler.ui.screen.home.mapper.QuizModeMapper
import com.example.quizler.data.remote.dto.CreateNewQuestionDto
import com.example.quizler.data.remote.dto.mapper.CreateNewQuestionDtoMapper
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionScreenState
import com.example.quizler.ui.screen.quiz.IQuizResultStateGenerator
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.ui.screen.quiz.QuizResultStateGenerator
import com.example.quizler.ui.screen.quiz.bundle.AnswerEntityBundle
import com.example.quizler.ui.screen.quiz.host.IQuizHost
import com.example.quizler.ui.screen.quiz.host.IQuizQuestionManager
import com.example.quizler.ui.screen.quiz.host.QuizHost
import com.example.quizler.ui.screen.quiz.host.QuizQuestionManager
import com.example.quizler.ui.screen.quiz.mapper.AnswerUiMapper
import com.example.quizler.ui.screen.quiz.mapper.QuestionBundleMapper
import com.example.quizler.ui.screen.quiz.mapper.QuestionUiMapper
import com.example.quizler.ui.screen.quiz.mapper.ReportTypeUiMapper
import com.example.quizler.util.mapper.DataMapper
import com.example.quizler.util.provider.AbstractResourceProvider
import com.example.quizler.util.provider.QQuizModeTitleProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UiModuleSingleton {

    /**
     * ===========================================================
     * ========================= Mapper ==========================
     * ===========================================================
     **/

    @Provides
    @Singleton
    fun provideQuizModeMapper(
        @QQuizModeTitleProvider titleProvider: AbstractResourceProvider<String>,
        drawableProvider: AbstractResourceProvider<Drawable>,
    ): DataMapper<BaseQuizModeEntity, QuizMode> {
        return QuizModeMapper(titleProvider, drawableProvider)
    }

    @Provides
    @Singleton
    fun provideAnswerUiMapper(): DataMapper<AnswerEntityBundle, Answer> {
        return AnswerUiMapper()
    }

    @Provides
    @Singleton
    fun provideQuestionUiMapper(): DataMapper<QuestionEntity, Question> {
        return QuestionUiMapper()
    }

    @Provides
    @Singleton
    fun provideReportTypeUiMapper(): DataMapper<ReportTypeEntity, ReportType> {
        return ReportTypeUiMapper()
    }

    @Provides
    @Singleton
    fun provideQuestionBundleMapper(
        questionMapper: DataMapper<QuestionEntity, Question>,
        answerUiMapper: DataMapper<AnswerEntityBundle, Answer>
    ): DataMapper<QuestionWithAnswersEntity, QuestionBundle> {
        return QuestionBundleMapper(questionMapper, answerUiMapper)
    }

    @Singleton
    @Provides
    fun provideAnswerRecordMapper(): DataMapper<AnswerRecordDto, AnswerRecordEntity> {
        return AnswerRecordDtoMapper()
    }

    @Singleton
    @Provides
    fun provideResultRecordMapper(): DataMapper<ResultRecordDto, ResultRecordEntity> {
        return ResultRecordDtoMapper()
    }

    @Singleton
    @Provides
    fun provideQuizModeDropdownItemMapper(
        iconProvider: AbstractResourceProvider<Drawable>,
        @QQuizModeTitleProvider titleProvider: AbstractResourceProvider<String>
    ): DataMapper<BaseQuizModeEntity, ChosableItem.Content> {
        return QuizModeDropdownItemMapper(iconProvider, titleProvider)
    }

    @Singleton
    @Provides
    fun provideScoresUiMapper(): DataMapper<ScoreEntity, Score> {
        return ScoresUiMapper()
    }

    @Singleton
    @Provides
    fun provideReportedQuestionMapper(): DataMapper<InvalidQuestionReport, InvalidQuestionReportEntity> {
        return ReportedQuestionMapper()
    }

    @Singleton
    @Provides
    fun provideCreateNewQuestionDtoMapper(): DataMapper<CreateNewQuestionScreenState, CreateNewQuestionDto> {
        return CreateNewQuestionDtoMapper()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class UiModuleViewModel {

    @ViewModelScoped
    @Provides
    fun provideQuestionFilterManager(
        getQuestionsUseCase: GetQuestionsUseCase,
    ): IQuizQuestionManager {
        return QuizQuestionManager(getQuestionsUseCase)
    }

    @ViewModelScoped
    @Provides
    fun provideQuizHost(
        @QCoroutineScopeIo coroutineScope: CoroutineScope,
        quizResultStateGenerator: IQuizResultStateGenerator,
        questionFilterManager: IQuizQuestionManager,
        getReportTypesUseCase: GetReportTypesUseCase,
        getUsernameUseCase: GetUsernameUseCase,
        sendInvalidQuestionReportUseCase: SendInvalidQuestionReportUseCase
    ): IQuizHost {
        return QuizHost(
            coroutineScope,
            quizResultStateGenerator,
            questionFilterManager,
            getReportTypesUseCase,
            getUsernameUseCase,
            sendInvalidQuestionReportUseCase
        )
    }

    @ViewModelScoped
    @Provides
    fun provideQuizResultStateGenerator(
        @ApplicationContext context: Context,
    ): IQuizResultStateGenerator {
        return QuizResultStateGenerator(context)
    }
}
