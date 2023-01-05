package com.example.quizler.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.quizler.domain.QSendDataToServerWorker
import com.example.quizler.ui.screen.App
import com.example.quizler.ui.theme.QuizlerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @QSendDataToServerWorker
    @Inject
    lateinit var sendDataToServer: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizlerTheme {
                App()
            }
        }
        WorkManager.getInstance(this).enqueue(sendDataToServer)
    }
}
