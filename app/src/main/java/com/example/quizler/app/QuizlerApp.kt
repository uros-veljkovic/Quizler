package com.example.quizler.app

import android.app.Application
import com.example.di.dataModule
import com.example.di.domainModule
import com.example.di.utilModule
import com.example.domain.NetworkConnectivityWatcher
import com.example.quizler.di.appModule
import com.example.quizler.di.uiModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree

class QuizlerApp : Application() {

    private val connectivityWatcher: NetworkConnectivityWatcher by inject()

    override fun onCreate() {
        super.onCreate()
        createTimber()

        initDI()
        startWatchingConnectivity()
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@QuizlerApp)
            workManagerFactory()
            modules(utilModule, dataModule, domainModule, uiModule, appModule)
        }
    }

    private fun startWatchingConnectivity() {
        connectivityWatcher.observeConnectivity(this)
    }

    private fun createTimber() {
        Timber.plant(DebugTree())
    }
}
