package com.example.coursy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

// Création du fragment_historique.xml
class historique : Fragment(R.layout.fragment_historique) {

    // Pour la logique écran
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Je déclare un élément du xml à partir de son id , recyclerHistorique
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerHistorique)
        // Je gère l'affichage en liste verticale
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Je fais passer une fonction en paramètre avec adapter, Onclick
        // Je lui donne item , un objet de type HistoriqueItem
        recyclerView.adapter = HistoriqueAdapter(DataManager.historiqueListe) { item ->
            // Je déclare un élément vide pour y mettre une liste d'article
            var message = ""

            // Je mets des articles avec leur prix sur plusieurs ligne
            for (article in item.articles) {
                message += "${article.nom} - ${article.prix} €\n"
            }

            // Création d'une boite de dialogue
            AlertDialog.Builder(requireContext())
                // J'affiche la date
                .setTitle(item.date)
                .setMessage(message)
                // c'est un bouton simple
                .setPositiveButton("OK", null)
                // J'affiche la boite de dalogue
                .show()
        }

    }
}