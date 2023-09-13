package com.codespacepro.newscomposeapp.network.retrofit

import com.codespacepro.newscomposeapp.network.api.NewsApi
import com.codespacepro.newscomposeapp.utli.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}