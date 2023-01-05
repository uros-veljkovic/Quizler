package com.example.quizler.domain

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.quizler.domain.usecase.SendStoredDataToServerUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QSendDataToServerWorker

@HiltWorker
class SendDataToServerWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val useCase: SendStoredDataToServerUseCase
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val isDataSentToServer = useCase.invoke()
        return if (isDataSentToServer) Result.success() else Result.failure()
    }
}
