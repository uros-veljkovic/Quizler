package com.example.quizler.di

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.quizler.util.provider.AbstractResourceProvider
import com.example.quizler.util.provider.QQuizModeDescriptionProvider
import com.example.quizler.util.provider.QQuizModeTitleProvider
import com.example.quizler.util.provider.QuizModeDescriptionProvider
import com.example.quizler.util.provider.QuizModeIconProvider
import com.example.quizler.util.provider.QuizModeTitleProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QCoroutineScopeIo

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @QCoroutineScopeIo
    @Singleton
    @Provides
    fun provideIoCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Singleton
    @Provides
    @QQuizModeTitleProvider
    fun provideQuizModeTitleProvider(
        @ApplicationContext context: Context
    ): AbstractResourceProvider<String> {
        return QuizModeTitleProvider(context)
    }

    @Singleton
    @Provides
    @QQuizModeDescriptionProvider
    fun provideQuizModeDescriptionProvider(
        @ApplicationContext context: Context
    ): AbstractResourceProvider<String> {
        return QuizModeDescriptionProvider(context)
    }

    @Singleton
    @Provides
    fun provideQuizModeIconProvider(
        @ApplicationContext context: Context
    ): AbstractResourceProvider<Drawable> {
        return QuizModeIconProvider(context)
    }
}
