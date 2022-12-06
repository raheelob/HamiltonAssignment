package com.example.currencyexchange.di

import com.example.currencyexchange.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class TokenServiceInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request = chain.request()
        newRequest = newRequest.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.API_KEY}"
            )
            .build()
        return chain.proceed(newRequest)
    }

}