package com.example.quizler.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.di.dataModule
import com.example.di.domainModule
import com.example.di.utilModule
import com.example.domain.NetworkConnectivityWatcher
import com.example.quizler.di.appModule
import com.example.quizler.di.uiModule
import com.example.quizler.util.SendDataToServerWorkerFactory
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
        initWorker()
    }

    private fun initWorker() {
        val workerFactory: SendDataToServerWorkerFactory = get()
        val configuration = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, configuration)
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@QuizlerApp)
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
