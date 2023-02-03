package com.example.quizler.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.quizler.BuildConfig
import com.example.quizler.data.local.NetworkRepository
import com.example.quizler.data.local.QuizLocalRepository
import com.example.quizler.data.local.db.dao.QuizlerDatabase
import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.local.entity.mapper.AnswerRecordEntityMapper
import com.example.quizler.data.local.entity.mapper.ResultRecordEntityMapper
import com.example.quizler.data.preferences.AppDataStorePreferences
import com.example.quizler.data.preferences.DataStoreUserPreferences
import com.example.quizler.data.remote.FakeQuizRemoteRepository
import com.example.quizler.data.remote.QFakeRemoteRepo
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.QuizRemoteRepository
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.data.remote.service.quizmode.QuizService
import com.example.quizler.domain.data.local.INetworkRepository
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.domain.preferences.IAppPreferences
import com.example.quizler.domain.preferences.IDataSyncCoordinator
import com.example.quizler.domain.preferences.IUserPreferences
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.mapper.DataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    /**
     * ===========================================================
     * ========================= Prefs ===========================
     * ===========================================================
     **/

    private val Context.userDatastore by preferencesDataStore(name = "user_preferences")
    private val Context.appDataStore by preferencesDataStore(name = "app_preferences")

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): IUserPreferences {
        return DataStoreUserPreferences(context.userDatastore)
    }

    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): IAppPreferences {
        return AppDataStorePreferences(context.appDataStore)
    }

    @Singleton
    @Provides
    fun provideIDataManger(@ApplicationContext context: Context): IDataSyncCoordinator {
        return provideAppPreferences(context)
    }

    /**
     * ===========================================================
     * ========================= Local ===========================
     * ===========================================================
     **/

    @Provides
    @Singleton
    fun provideQuizModeDatabase(@ApplicationContext context: Context): QuizlerDatabase {
        return Room.databaseBuilder(context, QuizlerDatabase::class.java, "db_quizler")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideLocalRepository(quizlerDatabase: QuizlerDatabase): IQuizLocalRepository {
        return QuizLocalRepository(quizlerDatabase)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(): INetworkRepository {
        return NetworkRepository()
    }

    /**
     * ===========================================================
     * ======================== Mappers ==========================
     * ===========================================================
     **/

    @Provides
    @Singleton
    fun provideResultRecordEntityMapper(): DataMapper<ResultRecordEntity, ResultRecordDto> {
        return ResultRecordEntityMapper()
    }

    @Provides
    @Singleton
    fun provideAnswerRecordEntityMapper(): DataMapper<AnswerRecordEntity, AnswerRecordDto> {
        return AnswerRecordEntityMapper()
    }

    /**
     * ===========================================================
     * ======================== Remote ===========================
     * ===========================================================
     **/

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val baseUrl = if (BuildConfig.DEBUG) "http://192.168.1.113:2000" else BuildConfig.SERVER_URL

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideQuizModeService(retrofit: Retrofit): QuizService {
        return retrofit.create(
            QuizService::class.java
        )
    }

    @Singleton
    @Provides
    @QRealRemoteRepo
    fun provideRemoteRepository(
        quizService: QuizService,
        networkActionHandler: INetworkActionHandler
    ): IQuizRemoteRepository {
        return QuizRemoteRepository(quizService, networkActionHandler)
    }

    @Singleton
    @Provides
    @QFakeRemoteRepo
    fun provideFakeRemoteRepository(): IQuizRemoteRepository {
        return FakeQuizRemoteRepository()
    }

    companion object {
        private const val READ_TIMEOUT = 15L
        private const val WRITE_TIMEOUT = 15L
        private const val CONNECT_TIMEOUT = 5L
    }
}
