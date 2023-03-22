package com.example.quizler.di

import com.example.domain.mapper.AnswerDomainMapper
import com.example.domain.mapper.CreateNewQuestionMappers
import com.example.domain.mapper.QuestionDomainMapper
import com.example.domain.mapper.QuestionWithAnswersUiMapper
import com.example.domain.mapper.ReportTypeUiMapper
import com.example.domain.mapper.ReportedQuestionMapper
import com.example.domain.mapper.ScoresEntityMapper
import com.example.quizler.ui.mapper.QuizModeDropdownItemMapper
import com.example.quizler.ui.screen.home.mapper.QuizModeMapper
import com.example.quizler.ui.screen.quiz.IQuizResultStateGenerator
import com.example.quizler.ui.screen.quiz.QuizResultStateGenerator
import com.example.quizler.ui.screen.quiz.host.IQuizHost
import com.example.quizler.ui.screen.quiz.host.IQuizQuestionManager
import com.example.quizler.ui.screen.quiz.host.QuizHost
import com.example.quizler.ui.screen.quiz.host.QuizQuestionManager
import com.example.util.di.Named
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val uiModuleSingleton = module {
    single {
        QuizModeMapper(
            get(named(Named.QUIZ_MODE_TITLE_PROVIDER.value)),
            get(named(Named.QUIZ_MODE_ICON_PROVIDER.value))
        )
    }
    single { AnswerDomainMapper() }
    single { QuestionDomainMapper() }
    single { ReportTypeUiMapper() }
    single { QuestionWithAnswersUiMapper(get(), get()) }

    single {
        QuizModeDropdownItemMapper(
            get(named(Named.QUIZ_MODE_ICON_PROVIDER.value)),
            get(named(Named.QUIZ_MODE_TITLE_PROVIDER.value))
        )
    }
    single { ScoresEntityMapper() }
    single { ReportedQuestionMapper() }
    single { CreateNewQuestionMappers() }
}

private val quizViewModelModule = module {
    factory<IQuizQuestionManager> { QuizQuestionManager(get()) }
    factory<IQuizHost> {
        QuizHost(
            CoroutineScope(Dispatchers.IO), get(), get(), get(), get(), get()
        )
    }
    factory<IQuizResultStateGenerator> { QuizResultStateGenerator(androidContext()) }
}

val uiModule = module {
    includes(uiModuleSingleton, quizViewModelModule)
}
