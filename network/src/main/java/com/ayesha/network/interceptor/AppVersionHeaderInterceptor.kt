package com.ayesha.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AppVersionHeaderInterceptor :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newRequest: Request = request
            .newBuilder()
            .build()

        return chain.proceed(newRequest)
    }

}
