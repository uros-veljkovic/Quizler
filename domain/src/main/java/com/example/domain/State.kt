package com.example.domain

import androidx.annotation.StringRes

sealed class State<T>(
    val data: T? = null,
    val error: Throwable? = null,
    @StringRes val messageResId: Int? = null,
    @StringRes val messageDescriptionResId: Int? = null,
) {

    class Success<T>(data: T) : State<T>(data)

    class Loading<T>(data: T? = null) : State<T>(data)

    class Error<T>(
        throwable: Throwable? = null,
        data: T? = null,
        messageTitleResId: Int? = null,
        messageDescriptionResId: Int? = null
    ) : State<T>(data, throwable, messageTitleResId, messageDescriptionResId)

    companion object {
        fun isAllSuccess(vararg states: State<*>): Boolean {
            return states.all { it is Success }
        }
    }
}
