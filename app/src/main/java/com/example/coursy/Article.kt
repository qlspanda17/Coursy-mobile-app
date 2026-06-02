package com.example.coursy



data class Article(
    val nom: String,
    val prix: Double,
    val quantite: Int,
    val unite: String,
    val categorie: String,
    var estAchete: Boolean
)
