package com.example.quizler.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.usecase.ISendStoredDataToServerUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SendDataToServerWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val useCase: ISendStoredDataToServerUseCase by inject()

    override suspend fun doWork(): Result {
        val isDataSentToServer = useCase.invoke()
        return if (isDataSentToServer) Result.success() else Result.failure()
    }
}
