package com.example.quizler.di

import com.example.domain.NetworkConnectivityWatcher
import com.example.quizler.InfoBannerDataMapper
import com.example.quizler.ui.screen.AppViewModel
import com.example.quizler.ui.screen.home.HomeViewModel
import com.example.quizler.ui.screen.newquestion.ChoosableCategoryItemsProvider
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.onboarding.OnboardingViewModel
import com.example.quizler.ui.screen.onboarding.signin.SignInViewModel
import com.example.quizler.ui.screen.onboarding.splash.SplashViewModel
import com.example.quizler.ui.screen.quiz.QuizViewModel
import com.example.quizler.ui.screen.score.ChoosableModeItemsProvider
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.score.ScoresUiMapper
import com.example.quizler.util.SendDataToServerWorkerFactory
import com.example.quizler.utils.signin.manager.GoogleSignInManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Data mapper
    single { InfoBannerDataMapper() }
    single { ScoresUiMapper() }

    // Worker
    single { SendDataToServerWorkerFactory(getKoin()) }

    // Connectivity
    single { NetworkConnectivityWatcher(get()) }

    single { ChoosableModeItemsProvider(get(), get(), get(), get()) }
    single { ChoosableCategoryItemsProvider(get(), get()) }

    // Sign in
    single { GoogleSignInManager() }

    viewModel { AppViewModel() }
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { OnboardingViewModel(get()) }
    viewModel { SignInViewModel() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { CreateNewQuestionViewModel(get(), get(), get()) }
    viewModel { QuizViewModel(get(), get(), get(), get()) }
    viewModel { ScoreViewModel(get(), get(), get()) }
}
