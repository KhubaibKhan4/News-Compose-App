package com.codespacepro.newscomposeapp.repository

import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.network.retrofit.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor() {



    suspend fun getNews(
        apiKey: String,
        query: String,
        country: String,
        category: String
    ): Response<News> {
        return RetrofitInstance.api.getNews(apiKey, query, country, category)
    }

    suspend fun getCrypto(
        apiKey: String
    ): Response<News> {
        return RetrofitInstance.api.getCrypto(apiKey)
    }

    suspend fun getArchive(
        apiKey: String,
        country: String
    ): Response<News> {
        return RetrofitInstance.api.getArchive(apiKey, country)
    }

}