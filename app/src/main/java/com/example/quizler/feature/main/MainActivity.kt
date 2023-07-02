package com.example.quizler.feature.main

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.ui.screen.App
import com.example.quizler.util.SendDataToServerWorker
import com.example.quizler.utils.signin.manager.token.refresh.RefreshTokenWorker
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            QuizlerTheme {
                App()
            }
        }
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        requestUpdateIfExists()
        enqueueRefreshTokenWork()
        enqueueSendDataToServerWork()
    }

    private fun enqueueSendDataToServerWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = OneTimeWorkRequestBuilder<SendDataToServerWorker>()
            .setConstraints(constraints = constraints)
            .build()

        WorkManager.getInstance(this).enqueue(work)
    }

    private fun enqueueRefreshTokenWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = PeriodicWorkRequestBuilder<RefreshTokenWorker>(45, TimeUnit.MINUTES)
            .setInitialDelay(30, TimeUnit.MINUTES) // Delay the start to align with your token expiration
            .setConstraints(constraints = constraints)
            .build()

        WorkManager.getInstance(this).enqueue(work)
    }

    override fun onResume() {
        super.onResume()
        // TODO: Move to new class QuizlerAppUpdateManager
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    IMMEDIATE,
                    this,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    private fun requestUpdateIfExists() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    IMMEDIATE,
                    this,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                // Do nothing
            }
        }
    }

    companion object {
        const val MY_REQUEST_CODE = 1234
    }
}
