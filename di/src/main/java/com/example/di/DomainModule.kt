package com.example.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.preferences.AppDataStorePreferences
import com.example.data.preferences.IAppPreferences
import com.example.data.preferences.IDataSyncCoordinator
import com.example.data.preferences.IUserPreferences
import com.example.data.preferences.UserDataStorePreferences
import com.example.domain.date.DateManager
import com.example.domain.date.IDateManager
import com.example.domain.mapper.AnswerDtoMapper
import com.example.domain.mapper.AnswerRecordDomainMapper
import com.example.domain.mapper.AnswerRecordDtoMapper
import com.example.domain.mapper.CategoryModeDtoMapper
import com.example.domain.mapper.CategoryModeMapper
import com.example.domain.mapper.DifficultyModeDtoMapper
import com.example.domain.mapper.DifficultyModeMapper
import com.example.domain.mapper.LengthModeDtoMapper
import com.example.domain.mapper.LengthModeMapper
import com.example.domain.mapper.QuestionDtoMappers
import com.example.domain.mapper.QuestionWithAnswersDtoMapper
import com.example.domain.mapper.ReportTypeDtoMapper
import com.example.domain.mapper.ResultRecordDtoMapper
import com.example.domain.mapper.ResultRecordEntityMapper
import com.example.domain.mapper.ResultRecordMapper
import com.example.domain.mapper.ScoreDtoMapper
import com.example.domain.network.FetchAndCacheManager
import com.example.domain.network.IFetchAndCacheManager
import com.example.domain.provider.AbstractResourceProvider
import com.example.domain.provider.drawable.QuizModeIconProvider
import com.example.domain.provider.string.QuizModeDescriptionProvider
import com.example.domain.provider.string.QuizModeTitleProvider
import com.example.domain.provider.string.ReportTypeTitleResourceProvider
import com.example.domain.usecase.CacheTokenUseCase
import com.example.domain.usecase.CreateNewQuestionUseCase
import com.example.domain.usecase.DetermainNextDestinationScreenUseCase
import com.example.domain.usecase.GetHasInternetConnectionUseCase
import com.example.domain.usecase.GetModesCategoryUseCase
import com.example.domain.usecase.GetModesDifficultyUseCase
import com.example.domain.usecase.GetModesLengthUseCase
import com.example.domain.usecase.GetQuestionsUseCase
import com.example.domain.usecase.GetReportTypesUseCase
import com.example.domain.usecase.GetScoresUseCase
import com.example.domain.usecase.GetUsernameUseCase
import com.example.domain.usecase.HandleStartupDataUseCase
import com.example.domain.usecase.ICacheTokenUseCase
import com.example.domain.usecase.ICreateNewQuestionUseCase
import com.example.domain.usecase.IDetermainNextDestinationScreenUseCase
import com.example.domain.usecase.IGetHasInternetConnectionUseCase
import com.example.domain.usecase.IGetModesCategoryUseCase
import com.example.domain.usecase.IGetModesDifficultyUseCase
import com.example.domain.usecase.IGetModesLengthUseCase
import com.example.domain.usecase.IGetQuestionsUseCase
import com.example.domain.usecase.IGetReportTypesUseCase
import com.example.domain.usecase.IGetScoresUseCase
import com.example.domain.usecase.IGetUsernameUseCase
import com.example.domain.usecase.IHandleStartupDataUseCase
import com.example.domain.usecase.ISaveAnswerRecordUseCase
import com.example.domain.usecase.ISaveResultRecordUseCase
import com.example.domain.usecase.ISaveUsernameUseCase
import com.example.domain.usecase.ISendInvalidQuestionReportUseCase
import com.example.domain.usecase.ISendStoredDataToServerUseCase
import com.example.domain.usecase.ISetHasInternetConnectionUseCase
import com.example.domain.usecase.ISignInUseCase
import com.example.domain.usecase.SaveAnswerRecordUseCase
import com.example.domain.usecase.SaveResultRecordUseCase
import com.example.domain.usecase.SaveUsernameUseCase
import com.example.domain.usecase.SendInvalidQuestionReportUseCase
import com.example.domain.usecase.SendStoredDataToServerUseCase
import com.example.domain.usecase.SetHasInternetConnectionUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.domain.wrapper.ContextWrapper
import com.example.util.di.Named
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val Context.userPreferencesStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
val Context.appPreferencesStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

val domainModule = module {

    // Action handler
    single<IFetchAndCacheManager> { FetchAndCacheManager(Dispatchers.IO, get(), get()) }

    // Resource provider
    single<AbstractResourceProvider<String>>(named(Named.REPORT_TYPE_TITLE_PROVIDER.value)) {
        ReportTypeTitleResourceProvider(
            ContextWrapper(androidContext())
        )
    }

    // Proivder
    single<AbstractResourceProvider<String>>(named(Named.QUIZ_MODE_TITLE_PROVIDER.value)) {
        QuizModeTitleProvider(ContextWrapper(androidContext()))
    }
    single<AbstractResourceProvider<String>>(named(Named.QUIZ_MODE_DESCRIPTION_PROVIDER.value)) {
        QuizModeDescriptionProvider(ContextWrapper(androidContext()))
    }
    single<AbstractResourceProvider<Int>>(named(Named.QUIZ_MODE_ICON_PROVIDER.value)) {
        QuizModeIconProvider(ContextWrapper(androidContext()))
    }

    // Mappers
    single { AnswerDtoMapper() }
    single { QuestionDtoMappers() }
    single { QuestionWithAnswersDtoMapper(get(), get()) }
    single { CategoryModeDtoMapper() }
    single { CategoryModeMapper() }
    single { LengthModeDtoMapper() }
    single { LengthModeMapper() }
    single { DifficultyModeDtoMapper() }
    single { DifficultyModeMapper() }
    single { AnswerRecordDomainMapper() }
    single { AnswerRecordDtoMapper() }
    single { ResultRecordDtoMapper() }
    single { ResultRecordEntityMapper() }
    single { ResultRecordMapper() }
    single { ScoreDtoMapper() }
    single { ReportTypeDtoMapper(get(named(Named.REPORT_TYPE_TITLE_PROVIDER.value))) }

    // Preferences
    single<IUserPreferences> { UserDataStorePreferences(androidContext().userPreferencesStore) }
    single<IAppPreferences> { AppDataStorePreferences(androidContext().appPreferencesStore) }

    // Data synchronisation
    single<IDataSyncCoordinator> { get<IAppPreferences>() }

    // Use Cases
    single<ISignInUseCase> { SignInUseCase(get(), get(), get()) }
    single<IDetermainNextDestinationScreenUseCase> { DetermainNextDestinationScreenUseCase(get(), get()) }
    single<ICacheTokenUseCase> { CacheTokenUseCase(get()) }
    single<ICreateNewQuestionUseCase> { CreateNewQuestionUseCase(get(), get()) }
    single<IGetModesCategoryUseCase> { GetModesCategoryUseCase(get(), get(), get(), get(), get()) }
    single<IGetModesDifficultyUseCase> { GetModesDifficultyUseCase(get(), get(), get(), get(), get()) }
    single<IGetModesLengthUseCase> { GetModesLengthUseCase(get(), get(), get(), get(), get()) }
    single<IGetQuestionsUseCase> { GetQuestionsUseCase(get(), get(), get(), get(), get(), get(), get()) }
    single<IGetReportTypesUseCase> { GetReportTypesUseCase(get(), get(), get(), get(), get()) }
    single<IGetScoresUseCase> { GetScoresUseCase(get(), get(), get(), get(), get()) }
    single<IGetUsernameUseCase> { GetUsernameUseCase(get()) }
    single<ISaveUsernameUseCase> { SaveUsernameUseCase(get()) }
    single<ISaveAnswerRecordUseCase> { SaveAnswerRecordUseCase(get(), get(), get(), get()) }
    single<ISaveResultRecordUseCase> { SaveResultRecordUseCase(get(), get(), get(), get(), get()) }
    single<ISendStoredDataToServerUseCase> { SendStoredDataToServerUseCase(get(), get(), get(), get()) }
    single<ISendInvalidQuestionReportUseCase> { SendInvalidQuestionReportUseCase(get(), get(), get()) }
    single<IGetHasInternetConnectionUseCase> { GetHasInternetConnectionUseCase(get()) }
    single<ISetHasInternetConnectionUseCase> { SetHasInternetConnectionUseCase(get()) }

    single<IHandleStartupDataUseCase> {
        HandleStartupDataUseCase(
            listOf(
                get<IGetModesDifficultyUseCase>(),
                get<IGetModesLengthUseCase>(),
                get<IGetModesCategoryUseCase>(),
                get<IGetScoresUseCase>(),
                get<IGetReportTypesUseCase>(),
                get<IGetQuestionsUseCase>()
            )
        )
    }

    // Date Manager
    single<IDateManager> { DateManager() }
}
