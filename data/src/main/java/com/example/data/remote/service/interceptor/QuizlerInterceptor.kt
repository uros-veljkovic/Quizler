package com.example.data.remote.service.interceptor

import com.example.data.preferences.IAppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class QuizlerInterceptor(
    private val preferences: IAppPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getTokenSynchronously() ?: ""
        val modifiedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
