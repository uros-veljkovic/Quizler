package com.example.quizler.di

import com.example.domain.NetworkConnectivityWatcher
import com.example.quizler.InfoBannerDataMapper
import com.example.quizler.ui.screen.AppViewModel
import com.example.quizler.ui.screen.home.ModesViewModel
import com.example.quizler.ui.screen.newquestion.ChoosableCategoryItemsProvider
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.onboarding.CreateProfileViewModel
import com.example.quizler.ui.screen.onboarding.empty.EmptyViewModel
import com.example.quizler.ui.screen.onboarding.signin.SignInViewModel
import com.example.quizler.ui.screen.onboarding.splash.SplashViewModel
import com.example.quizler.ui.screen.quiz.QuizViewModel
import com.example.quizler.ui.screen.score.ChoosableModeItemsProvider
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.score.ScoresUiMapper
import com.example.quizler.ui.screen.settings.AppReviewHandler
import com.example.quizler.ui.screen.settings.EmailManager
import com.example.quizler.ui.screen.settings.IAppReviewHandler
import com.example.quizler.ui.screen.settings.IEmailManager
import com.example.quizler.ui.screen.settings.IShareQuizlerLinkManager
import com.example.quizler.ui.screen.settings.IWebPageOpener
import com.example.quizler.ui.screen.settings.SettingsViewModel
import com.example.quizler.ui.screen.settings.ShareQuizlerLinkManager
import com.example.quizler.ui.screen.settings.WebPageOpener
import com.example.quizler.util.SendDataToServerWorker
import com.example.quizler.utils.signin.manager.GoogleSignInManager
import com.example.quizler.utils.signin.manager.token.refresh.FacebookRefreshTokenStrategy
import com.example.quizler.utils.signin.manager.token.refresh.GoogleRefreshTokenStrategy
import com.example.quizler.utils.signin.manager.token.refresh.IRefreshTokenFacade
import com.example.quizler.utils.signin.manager.token.refresh.IRefreshTokenStrategy
import com.example.quizler.utils.signin.manager.token.refresh.RefreshTokenFacade
import com.example.quizler.utils.signin.manager.token.refresh.RefreshTokenWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    // region Data mapper
    single { InfoBannerDataMapper() }
    single { ScoresUiMapper() }
    // endregion

    // region Connectivity
    single { NetworkConnectivityWatcher(get()) }
    single { ChoosableModeItemsProvider(get(), get(), get(), get()) }
    single { ChoosableCategoryItemsProvider(get(), get()) }
    // endregion

    // region Sign in
    single { GoogleSignInManager() }
    // endregion

    // region ViewModel
    viewModel { AppViewModel() }
    viewModel { EmptyViewModel(get()) }
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { CreateProfileViewModel(get()) }
    viewModel { ModesViewModel(get(), get(), get(), get()) }
    viewModel { CreateNewQuestionViewModel(get(), get(), get()) }
    viewModel { QuizViewModel(get(), get(), get(), get()) }
    viewModel { ScoreViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get()) }
    // endregion

    // region TokenRefresh
    single<IRefreshTokenStrategy>(named(GoogleRefreshTokenStrategy.NAME)) {
        GoogleRefreshTokenStrategy(
            androidContext(),
            get()
        )
    }
    single<IRefreshTokenStrategy>(named(FacebookRefreshTokenStrategy.NAME)) {
        FacebookRefreshTokenStrategy()
    }
    single<IRefreshTokenFacade> {
        RefreshTokenFacade(
            get(named(GoogleRefreshTokenStrategy.NAME)),
            get(named(GoogleRefreshTokenStrategy.NAME)),
            get()
        )
    }
    // endregion

    // region Settings
    single<IShareQuizlerLinkManager> { ShareQuizlerLinkManager() }
    single<IAppReviewHandler> { AppReviewHandler() }
    single<IEmailManager> { EmailManager() }
    single<IWebPageOpener> { WebPageOpener() }
    // endregion

    // region Workers
    worker(named("RefreshTokenWorker")) {
        RefreshTokenWorker(androidContext(), get(), get())
    }
    worker(named("SendDataToServerWorker")) {
        SendDataToServerWorker(androidContext(), get())
    }
    //endregion
}
