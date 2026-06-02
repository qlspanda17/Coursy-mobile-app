package com.example.coursy

data class HistoriqueItem(
    val date: String,
    val total: Double,
    val articles: List<Article>
)
