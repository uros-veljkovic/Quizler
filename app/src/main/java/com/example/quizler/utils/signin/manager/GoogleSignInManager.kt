package com.example.quizler.utils.signin.manager

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GoogleSignInManager : ISignInManager {

    private val signInState: MutableSharedFlow<SignInState> = MutableSharedFlow()

    override fun startSignIn(context: Context) {

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, signInOptions)
    }

    override fun getSignInState(): SharedFlow<SignInState> {
        return signInState.asSharedFlow()
    }
}
