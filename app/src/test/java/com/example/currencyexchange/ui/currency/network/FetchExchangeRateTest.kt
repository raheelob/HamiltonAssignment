package com.example.currencyexchange.ui.currency.network

import com.example.currencyexchange.data.api.CurrencyService
import com.example.currencyexchange.di.TokenServiceInterceptor
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class FetchExchangeRateTest {
    private lateinit var service: CurrencyService
    private lateinit var server: MockWebServer

    private fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun provideApiInterceptor() = Interceptor {
        val originalRequest = it.request()
        val newHttpUrl = originalRequest.url.newBuilder() // add api key here as query parameter...
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        it.proceed(newRequest)
    }

    private fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        apiInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(TokenServiceInterceptor())
            .addNetworkInterceptor(apiInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .client(provideOkHttpClient(provideLoggingInterceptor(), provideApiInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getTopStoriesListRequest() {
        runBlocking {
            enqueueMockResponse("ExchangeRateResponse.json")
            val responseBody = service.getCurrencyExchangeRate()
            assertTrue(responseBody.toString().isNotEmpty())
            val request = server.takeRequest()
            assertTrue(request.path.equals("/latest/USD"))
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}