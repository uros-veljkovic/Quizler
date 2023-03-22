package com.example.di

import androidx.room.Room
import com.example.data.local.INetworkRepository
import com.example.data.local.IQuizLocalRepository
import com.example.data.local.NetworkRepository
import com.example.data.local.QuizLocalRepository
import com.example.data.local.db.dao.QuizlerDatabase
import com.example.data.local.entity.mapper.AnswerRecordEntityMapper
import com.example.data.local.entity.mapper.ResultRecordEntityMapper
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.QuizRemoteRepository
import com.example.data.remote.actionhandler.INetworkActionHandler
import com.example.data.remote.actionhandler.NetworkActionHandler
import com.example.data.remote.service.quizmode.QuizService
import com.example.domain.network.inspector.NetworkInspector
import com.example.util.network.inspector.INetworkInspector
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single<INetworkInspector> { NetworkInspector(androidContext()) }
    single<INetworkActionHandler> { NetworkActionHandler(Dispatchers.IO, get(), get()) }

    // Local repository
    single {
        Room.databaseBuilder(androidContext(), QuizlerDatabase::class.java, "db_quizler")
            .fallbackToDestructiveMigration().build()
    }
    single<IQuizLocalRepository> { QuizLocalRepository(get()) }

    // Remote repository
    single<IQuizRemoteRepository> { QuizRemoteRepository(get(), get()) }

    // Network repository
    single<INetworkRepository> { NetworkRepository() }

    single { ResultRecordEntityMapper() }
    single { AnswerRecordEntityMapper() }

    single {
        OkHttpClient().newBuilder()
            .connectTimeout(5L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(QuizService::class.java) }
}
