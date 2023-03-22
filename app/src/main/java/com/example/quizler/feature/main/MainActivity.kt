package com.example.quizler.feature.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.ui.screen.App

class MainActivity : ComponentActivity() {

    // TODO: Think about how to handle injection without having to
    //  mark all classes with @Inject annotation
//    @QSendDataToServerWorker
//    @Inject
//    lateinit var sendDataToServer: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            QuizlerTheme {
                App()
            }
        }
//        WorkManager.getInstance(this).enqueue(sendDataToServer)
    }
}
