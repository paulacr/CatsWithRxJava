package net.paulacr.fluffycat.network

import net.paulacr.fluffycat.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader(API_KEY_NAME, BuildConfig.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val API_KEY_NAME = "x-api-key"
    }
}