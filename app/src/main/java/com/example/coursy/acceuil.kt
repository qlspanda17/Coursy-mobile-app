package com.example.coursy

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Permet de connecter le fichier fragment_acceuil.xml à la classe
class acceuil : Fragment(R.layout.fragment_acceuil) {

    // Une seule liste d'articles
    val liste = DataManager.listeArticles
    lateinit var adapter: ArticleAdapter

    // Fonction qui calcule le total
    fun calculerTotal(): Double {
        var total = 0.0
        for (article in liste) {
            total = total + article.prix
        }
        return total
    }

    // Fonction qui est exécutée par Android, logique principale de l'écran
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupère les éléments du fichier xml
        val recyclerView = view.findViewById<RecyclerView>(R.id.recylerview1)
        val textTotal = view.findViewById<TextView>(R.id.textTotal)
        val btnValider = view.findViewById<Button>(R.id.btnValider)

        // Affiche les articles en liste verticale
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Affichage du total
        textTotal.text = "Total : ${calculerTotal()} €"

        // Adapter + suppression
        adapter = ArticleAdapter(liste) { position ->

            AlertDialog.Builder(requireContext())
                .setTitle("Supprimer")
                .setMessage("Supprimer cet article ?")

                .setPositiveButton("Oui") { _, _ ->

                    // Suppression de l'article
                    liste.removeAt(position)

                    // Mise à jour de l'affichage
                    adapter.notifyDataSetChanged()

                    // Mise à jour du total
                    textTotal.text = "Total : ${calculerTotal()} €"
                }

                .setNegativeButton("Non", null)
                .show()
        }

        recyclerView.adapter = adapter

        // Bouton VALIDER → historique
        btnValider.setOnClickListener {

            // Vérifie si la liste est vide
            if (liste.isEmpty()) {
                Toast.makeText(requireContext(), "Aucun article à valider", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Date actuelle
            val date = java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date())

            // Calcul total
            val total = calculerTotal()

            // Copie de la liste
            val copie = liste.toList()

            // Ajout dans l'historique
            DataManager.historiqueListe.add(
                HistoriqueItem(date, total, copie)
            )

            // On vide la liste après validation
            liste.clear()

            // Mise à jour de l'affichage
            adapter.notifyDataSetChanged()

            textTotal.text = "Total : 0 €"
        }

        // Bouton Ajouter
        val bouton = view.findViewById<FloatingActionButton>(R.id.btnAjouter)

        bouton.setOnClickListener {

            val vue = layoutInflater.inflate(R.layout.ajout_article, null)

            val inputNom = vue.findViewById<EditText>(R.id.inputNom)
            val inputPrix = vue.findViewById<EditText>(R.id.inputPrix)
            val inputQuantite = vue.findViewById<EditText>(R.id.inputQuantite)
            val inputUnite = vue.findViewById<EditText>(R.id.inputUnite)
            val spinner = vue.findViewById<Spinner>(R.id.spinnerCategorie)

            val categories = DataManager.categories

            val adapterSpinner = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            )

            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapterSpinner

            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Ajouter un article")
            dialog.setView(vue)

            dialog.setPositiveButton("Ajouter") { _, _ ->

                val nom = inputNom.text.toString()

                // --- PRIX ---
                var prix = 0.0
                val textePrix = inputPrix.text.toString()

                if (textePrix.isNotEmpty()) {
                    try {
                        prix = textePrix.toDouble()
                    } catch (e: Exception) {
                        prix = 0.0
                    }
                }

                // --- QUANTITÉ ---
                var quantite = 1
                val texteQuantite = inputQuantite.text.toString()

                if (texteQuantite.isNotEmpty()) {
                    try {
                        quantite = texteQuantite.toInt()
                    } catch (e: Exception) {
                        quantite = 1
                    }
                }

                val unite = inputUnite.text.toString()
                val categorie = spinner.selectedItem.toString()

                // Ajoute l'article
                liste.add(
                    Article(
                        nom,
                        prix,
                        quantite,
                        unite,
                        categorie,
                        false
                    )
                )

                adapter.notifyDataSetChanged()

                textTotal.text = "Total : ${calculerTotal()} €"
            }

            dialog.setNegativeButton("Annuler", null)
            dialog.show()
        }
    }
}