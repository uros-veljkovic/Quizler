package com.example.data.util.network.actionhandler

import com.example.data.remote.actionhandler.NetworkActionHandler
import com.example.util.data.RepositoryResponse
import com.example.util.exceptions.ConnectivityException
import com.example.util.exceptions.ServerUnavailableException
import com.example.util.network.inspector.INetworkInspector
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkActionHandlerTest {
    private lateinit var networkInspector: INetworkInspector
    private lateinit var sut: NetworkActionHandler

    @Before
    fun setUp() {
        networkInspector = mockk()
        sut = NetworkActionHandler(UnconfinedTestDispatcher(), networkInspector)
    }

    @Test
    fun `invoke returns Success when response is successful`() = runTest {
        val result = sut.invoke {
            Response.success(Unit)
        }
        assertEquals(result::class, RepositoryResponse.Success::class)
    }

    @Test
    fun `invoke returns Failure with ConnectivityException when no connection`() = runTest {
        every { networkInspector.isConnected } returns false
        val result = sut.invoke { createErrorResponse<Unit>() }

        assert(result is RepositoryResponse.Failure && result.throwable::class == ConnectivityException::class)
    }

    @Test
    fun `invoke returns Failure with ServerUnavailableException when user is connected to internet`() = runTest {
        every { networkInspector.isConnected } returns true
        val result = sut.invoke { createErrorResponse<Unit>() }
        assert(result is RepositoryResponse.Failure && result.throwable::class == ServerUnavailableException::class)
    }

    private fun <T> createErrorResponse(): Response<T> =
        Response.error(401, ResponseBody.create(MediaType.get(""), byteArrayOf()))
}
