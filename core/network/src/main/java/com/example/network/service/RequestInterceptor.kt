package com.example.network.service

import com.example.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}