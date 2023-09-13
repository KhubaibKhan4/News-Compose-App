package com.codespacepro.newscomposeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<Response<News>> = MutableLiveData()

    fun getNews() {
        viewModelScope.launch {
            val response = repository.getNews("pub_238458c1ba1e35414e6402b4c551dc42d5af7",
                query = "developer",
                country = "us",
                category = "science")
            myResponse.value = response
        }
    }
}