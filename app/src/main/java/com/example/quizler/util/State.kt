package com.example.quizler.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.screen.info.InfoScreenVariants

@Immutable
sealed class State<T>(
    val data: T? = null,
    val error: Throwable? = null,
    @StringRes val messageResId: Int? = null,
    @StringRes val messageDescriptionResId: Int? = null,
) {

    @Immutable
    class Success<T>(data: T) : State<T>(data)

    @Immutable
    class Loading<T>(data: T? = null) : State<T>(data)

    @Immutable
    class Error<T>(
        throwable: Throwable? = null,
        data: T? = null,
        messageTitleResId: Int? = null,
        messageDescriptionResId: Int? = null
    ) : State<T>(data, throwable, messageTitleResId, messageDescriptionResId) {
        fun getInfoBanner(): InfoBannerData {
            return if (this.error is ConnectivityException)
                InfoBannerData.NoNetwork
            else
                InfoBannerData.ErrorLoadingContent
        }

        fun getInfoScreenData(): InfoScreenVariants {
            return if (this.error is ConnectivityException)
                InfoScreenVariants.NoNetwork
            else
                InfoScreenVariants.ErrorLoadingContent
        }
    }

    companion object {
        fun isAllSuccess(vararg states: State<*>): Boolean {
            return states.all { it is Success }
        }

        fun isAnyLoading(vararg states: State<*>): Boolean {
            return states.any { it is Loading }
        }

        fun isAnyError(vararg states: State<*>): Boolean {
            return states.any { it is Error }
        }
    }
}
