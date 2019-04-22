package net.paulacr.fluffycat.network

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("x-api-key", "5fee8d2c-a094-462c-b807-572877b7503b")
            .build()

        return chain.proceed(newRequest)
    }
}