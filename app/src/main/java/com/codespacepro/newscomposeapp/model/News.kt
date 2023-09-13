package com.codespacepro.newscomposeapp.model

data class News(
    val nextPage: String,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)