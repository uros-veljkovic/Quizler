package util

import com.example.data.preferences.IAppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAppPreferences() : IAppPreferences {

    private val _token: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _timeInMillis: MutableStateFlow<Long> = MutableStateFlow(0L)

    override suspend fun setToken(token: String) {
        _token.update { token }
    }

    override fun getToken(): Flow<String?> {
        return _token.asStateFlow()
    }

    override suspend fun setDataSyncTime(timeInMillis: Long) {
        _timeInMillis.update { timeInMillis }
    }

    override fun getDataSyncTime(): Flow<Long> {
        return _timeInMillis.asStateFlow()
    }
}
