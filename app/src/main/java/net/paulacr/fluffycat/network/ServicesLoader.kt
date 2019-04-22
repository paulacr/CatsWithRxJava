package net.paulacr.fluffycat.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ServicesLoader {

    companion object {

        fun loadServices() {
            initNetwork()
        }

        fun provideClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor .level = HttpLoggingInterceptor.Level.BODY
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            return okHttpClientBuilder.build()
        }

        fun initNetwork() {
            val client = provideClient()
            val retrofit = NetworkInstance.getBuilder()

            retrofit.create(ApiInterface::class.java)
        }
    }
}