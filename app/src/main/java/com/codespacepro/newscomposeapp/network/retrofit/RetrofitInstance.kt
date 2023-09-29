package com.codespacepro.newscomposeapp.network.retrofit

import com.codespacepro.newscomposeapp.network.api.NewsApi
import com.codespacepro.newscomposeapp.utli.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS) // Connection timeout
        .readTimeout(15, TimeUnit.SECONDS)    // Read timeout
        .writeTimeout(15, TimeUnit.SECONDS)   // Write timeout
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}