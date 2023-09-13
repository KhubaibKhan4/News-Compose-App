package com.codespacepro.newscomposeapp.model

data class Result(
    val article_id: String,
    val category: List<String>,
    val content: String,
    val country: List<String>,
    val creator: List<String>,
    val description: String,
    val image_url: String,
    val keywords: List<String>,
    val language: String,
    val link: String,
    val pubDate: String,
    val source_id: String,
    val source_priority: Int,
    val title: String,
    val video_url: Any
)