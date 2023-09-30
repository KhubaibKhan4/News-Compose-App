package com.codespacepro.newscomposeapp.network.api

import com.codespacepro.newscomposeapp.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("api/1/news")
    suspend fun getNews(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<News>

    @GET("api/1/crypto")
    suspend fun getCrypto(
        @Query("apikey") apiKey: String,
    ): Response<News>


    @GET("api/1/archive")
    suspend fun getArchive(
        @Query("apikey") apiKey: String,
        @Query("country") country: String,
    ): Response<News>
}