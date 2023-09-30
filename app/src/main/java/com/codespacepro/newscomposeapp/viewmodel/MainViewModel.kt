package com.codespacepro.newscomposeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val myResponse: MutableLiveData<Response<News>> = MutableLiveData()
    val myCryptoResponse: MutableLiveData<Response<News>> = MutableLiveData()
    val myArchiveResponse: MutableLiveData<Response<News>> = MutableLiveData()


    fun getNews(
        apiKey: String,
        query: String,
        country: String,
        category: String
    ) {
        viewModelScope.launch {
            val response = repository.getNews(
                apiKey,
                query,
                country,
                category
            )
            myResponse.value = response
        }
    }

    fun getCrypto(
        apiKey: String
    ) {
        viewModelScope.launch {
            val response = repository.getCrypto(apiKey)
            myCryptoResponse.value = response
        }
    }


    fun getArchive(
        apiKey: String,
        country: String
    ) {
        viewModelScope.launch {
            val response = repository.getArchive(apiKey, country)
            myArchiveResponse.value = response
        }
    }
}