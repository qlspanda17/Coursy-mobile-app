package com.example.coursy

object DataManager {


    val historiqueListe = mutableListOf<HistoriqueItem>()


    var categories = mutableListOf(
        "Oeufs, fruits et légumes",
        "Santé et beauté",
        "Boisson",
        "Boulangerie et desserts",
        "Charcuterie, lait et produits laitiers",
        "Non catégorisé",
        "Viandes",
        "Epicerie"
    )

    var suggestionsActive = true
    val listeArticles = mutableListOf<Article>()



}