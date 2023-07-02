package com.example.quizler.ui.screen.settings

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

interface IAppReviewHandler {
    fun getReviewStateus(): SharedFlow<Boolean>
    fun launchReviewFlow(activity: Activity, scope: CoroutineScope)
}

class AppReviewHandler : IAppReviewHandler {

    private val _reviewStatus = MutableSharedFlow<Boolean>()

    override fun getReviewStateus(): SharedFlow<Boolean> {
        return _reviewStatus.asSharedFlow()
    }

    override fun launchReviewFlow(activity: Activity, scope: CoroutineScope) {
        val reviewManager: ReviewManager = ReviewManagerFactory.create(activity)
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { result ->
            if (result.isSuccessful) {
                val reviewInfo = result.result
                val flow = reviewManager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    scope.launch {
                        _reviewStatus.emit(true)
                    }
                }
            } else {
                Timber.d(result.exception)
                scope.launch {
                    _reviewStatus.emit(false)
                }
            }
        }
    }
}
