package com.example.quizler.di

import com.example.domain.NetworkConnectivityWatcher
import com.example.quizler.InfoBannerDataMapper
import com.example.quizler.ui.screen.AppViewModel
import com.example.quizler.ui.screen.home.ModesViewModel
import com.example.quizler.ui.screen.newquestion.ChoosableCategoryItemsProvider
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.onboarding.CreateProfileViewModel
import com.example.quizler.ui.screen.onboarding.IOnboardingManager
import com.example.quizler.ui.screen.onboarding.OnboardingManager
import com.example.quizler.ui.screen.onboarding.empty.EmptyViewModel
import com.example.quizler.ui.screen.onboarding.signin.SignInViewModel
import com.example.quizler.ui.screen.onboarding.splash.SplashViewModel
import com.example.quizler.ui.screen.quiz.QuizViewModel
import com.example.quizler.ui.screen.score.ChoosableModeItemsProvider
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.score.ScoresUiMapper
import com.example.quizler.util.SendDataToServerWorker
import com.example.quizler.utils.signin.manager.GoogleSignInManager
import com.example.quizler.utils.signin.manager.token.RefreshTokenWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {

    // Data mapper
    single { InfoBannerDataMapper() }
    single { ScoresUiMapper() }

    // Connectivity
    single { NetworkConnectivityWatcher(get()) }

    single { ChoosableModeItemsProvider(get(), get(), get(), get()) }
    single { ChoosableCategoryItemsProvider(get(), get()) }

    // Sign in
    single { GoogleSignInManager() }
    single<IOnboardingManager> { OnboardingManager(androidApplication(), get()) }

    viewModel { AppViewModel() }
    viewModel { EmptyViewModel(get()) }
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { CreateProfileViewModel(get()) }
    viewModel { ModesViewModel(get(), get(), get(), get()) }
    viewModel { CreateNewQuestionViewModel(get(), get(), get()) }
    viewModel { QuizViewModel(get(), get(), get(), get()) }
    viewModel { ScoreViewModel(get(), get(), get()) }

    // Workers
    worker {
        RefreshTokenWorker(androidContext(), get())
    }
    worker {
        SendDataToServerWorker(androidContext(), get(), get())
    }
}
