package com.example.quizler.utils.signin.manager

import android.content.Context
import kotlinx.coroutines.flow.SharedFlow

enum class SignInState {
    Idle,
    InProgress,
    SignedIn,
    Failed
}

interface ISignInManager {
    fun startSignIn(context: Context)
    fun getSignInState(): SharedFlow<SignInState>
}