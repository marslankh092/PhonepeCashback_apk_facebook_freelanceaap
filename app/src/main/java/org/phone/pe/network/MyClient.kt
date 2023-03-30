package org.phone.pe.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyClient private constructor() {
    private val retrofit: Retrofit
    val api: Api
        get() = retrofit.create(Api::class.java)

    companion object {
//        x(R9crA&z76K
        const val BASE_URL = "https://techive.co/phonepe/api/v1/"
        private var myClient: MyClient? = null

        @get:Synchronized
        val instance: MyClient?
            get() {
                if (myClient == null) {
                    myClient = MyClient()
                }
                return myClient
            }
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(interceptor).build()
        val gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}