package com.example.quizler.feature.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.quizler.domain.QSendDataToServerWorker
import com.example.quizler.ui.screen.App
import com.example.quizler.ui.theme.QuizlerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @QSendDataToServerWorker
    @Inject
    lateinit var sendDataToServer: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            QuizlerTheme {
                App()
            }
        }
        WorkManager.getInstance(this).enqueue(sendDataToServer)
    }
}
