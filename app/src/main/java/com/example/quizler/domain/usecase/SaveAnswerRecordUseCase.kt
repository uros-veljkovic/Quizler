package com.example.quizler.domain.usecase

import android.util.Log
import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class SaveAnswerRecordUseCase @Inject constructor(
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<AnswerRecordDto, AnswerRecordEntity>
) {
    suspend operator fun invoke(record: AnswerRecordDto) {
        val result = remoteRepository.record(record)
        Log.d(TAG, "invoke: SAVING REMOTELY")
        if (result is RepositoryResponse.Failure) {
            Log.d(TAG, "invoke: SAVING LOCALLY INSTEAD")
            localRepository.insertAnswerRecord(mapper.map(record))
        }
    }

    companion object {
        const val TAG = "SaveAnswerRecordUseCase"
    }
}
