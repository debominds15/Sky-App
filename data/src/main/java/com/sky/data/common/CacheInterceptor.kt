package com.sky.data.common

import com.sky.data.common.Constants.CACHE_CONTROL_NO_CACHE
import com.sky.data.di.ApiModule
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val shouldUseCache =
            request.header(Constants.CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
        if (!shouldUseCache) return originalResponse

        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header(Constants.CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}
