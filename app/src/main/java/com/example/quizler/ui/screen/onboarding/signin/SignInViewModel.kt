package com.example.quizler.ui.screen.onboarding.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.quizler.utils.signin.manager.ISignInManager

class SignInViewModel(
    private val googleSignInManager: ISignInManager
) : ViewModel() {

    fun onGoogleSignInButtonClick(context: Context) {
        googleSignInManager.startSignIn(context)
    }
}
