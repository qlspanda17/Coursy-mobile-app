package com.example.coursy

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

// Lie ce fragment au fichier fragment_parametre.xml
class parametre : Fragment(R.layout.fragment_parametre) {

    // Fonction qui calcule les dépenses par catégorie
    fun calculDepenseParCategorie(): Map<String, Double> {
        val result = mutableMapOf<String, Double>()

        // Parcours de toutes les listes de l'historique
        for (historique in DataManager.historiqueListe) {
            // Parcours des articles de chaque liste
            for (article in historique.articles) {

                val categorie = article.categorie

                var ancien = 0.0

                val valeur = result[categorie]
                if (valeur != null) {
                    ancien = valeur
                }

                result[categorie] = ancien + article.prix

            }
        }

        return result
    }

    // Fonction qui calcule les dépenses par période (ici début de date)

    fun calculParSemaine(): Map<String, Double> {
        val result = mutableMapOf<String, Double>()

        for (historique in DataManager.historiqueListe) {

            val semaine = historique.date.substring(0, 5)

            var ancien = 0.0

            val valeur = result[semaine]
            if (valeur != null) {
                ancien = valeur
            }

            result[semaine] = ancien + historique.total
        }

        return result
    }


    // Affiche le graphique des dépenses par catégorie
    fun afficherGraphiqueParCategorie(pieChart: PieChart) {

        val entries = ArrayList<PieEntry>()
        val dataCategorie = calculDepenseParCategorie()

        // Si il n'y a pas de données
        if (dataCategorie.isEmpty()) {
            pieChart.clear()
            pieChart.setNoDataText("Ajoutez des articles\n et validez une liste\n pour voir les statistiques")
            return
        }

        // Ajoute chaque catégorie au graphique
        for ((categorie, total) in dataCategorie) {
            entries.add(PieEntry(total.toFloat(), categorie))
        }

        val dataSet = PieDataSet(entries, "Dépenses par catégorie")

        // Couleurs du graphique
        dataSet.setColors(
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.MAGENTA,
            Color.CYAN
        )

        val data = PieData(dataSet)

        // Applique les données au graphique
        pieChart.data = data
        pieChart.invalidate() // met à jour l'affichage
    }

    // Affiche le graphique des dépenses par semaine
    fun afficherGraphiqueParSemaine(pieChart: PieChart) {

        val entries = ArrayList<PieEntry>()
        val dataSemaine = calculParSemaine()

        // Si aucune donnée
        if (dataSemaine.isEmpty()) {
            pieChart.clear()
            pieChart.setNoDataText("Ajoutez des articles\n et validez une liste\n pour voir les statistiques")
            return
        }

        // Ajout des données
        for ((semaine, total) in dataSemaine) {
            entries.add(PieEntry(total.toFloat(), semaine))
        }

        val dataSet = PieDataSet(entries, "Dépenses par semaine")

        // Couleurs du graphique
        dataSet.setColors(
            Color.CYAN,
            Color.YELLOW,
            Color.LTGRAY,
            Color.DKGRAY
        )

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate() // actualise le graphique
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération des éléments du XML
        val input = view.findViewById<EditText>(R.id.inputCategorie)
        val btnAdd = view.findViewById<Button>(R.id.btnAddCategorie)
        val btnCategorie = view.findViewById<Button>(R.id.btnCategorie)
        val btnSemaine = view.findViewById<Button>(R.id.btnSemaine)
        val switchSuggestions = view.findViewById<Switch>(R.id.switchSuggestions)
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        // Configuration du graphique
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false

        pieChart.setHoleColor(Color.BLACK)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.legend.textColor = Color.WHITE
        pieChart.setCenterTextColor(Color.WHITE)

        // Bouton pour ajouter une catégorie
        btnAdd.setOnClickListener {
            val cat = input.text.toString()
            if (cat.isNotEmpty()) {
                // Ajoute dans DataManager
                DataManager.categories.add(cat)
                input.text.clear()
            }
        }

        // Initialise le switch avec la valeur actuelle
        switchSuggestions.isChecked = DataManager.suggestionsActive

        // Mise à jour du paramètre quand on appuie
        switchSuggestions.setOnCheckedChangeListener { _, isChecked ->
            DataManager.suggestionsActive = isChecked
        }

        // Graphique affiché par défaut
        afficherGraphiqueParCategorie(pieChart)

        // Bouton pour afficher graphique catégorie
        btnCategorie.setOnClickListener {
            afficherGraphiqueParCategorie(pieChart)
        }

        // Bouton pour afficher graphique semaine
        btnSemaine.setOnClickListener {
            afficherGraphiqueParSemaine(pieChart)
        }

    }
}