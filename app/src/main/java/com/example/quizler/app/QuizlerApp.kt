package com.example.quizler.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.quizler.BuildConfig
import com.example.quizler.domain.NetworkConnectivityWatcher
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class QuizlerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var connectivityWatcher: NetworkConnectivityWatcher

    override fun onCreate() {
        super.onCreate()
        createTimber()
        startWatchingConnectivity()
    }

    private fun startWatchingConnectivity() {
        connectivityWatcher.observeConnectivity(this)
    }

    private fun createTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory).build()
    }
}
