package com.example.coursy

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.ai
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class suggestions : Fragment(R.layout.fragment_suggestions) {

    // Espace d'exécution pour les tâche en arrière plan sans bloquer l'écran
    private val scope = MainScope()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val input = view.findViewById<EditText>(R.id.inputSuggestion)
        val btn = view.findViewById<Button>(R.id.btnGenerate)
        val result = view.findViewById<TextView>(R.id.resultSuggestion)

        btn.setOnClickListener {

            // vérifier si les suggestions sont activées
            if (!DataManager.suggestionsActive) {
                result.text = "Les suggestions sont désactivées dans les paramètres"
                return@setOnClickListener
            }
            // Déclare le texte à saisir
            val texte = input.text.toString()

            if (texte.isEmpty()) {
                result.text = "Écris quelque chose !"
                return@setOnClickListener
            }

            result.text = "Chargement..."

            // code pour l'intégration IA
            // Lance une tache en parallèle pour pas de blocage
            scope.launch {

                try {

                    val model = Firebase.ai(
                        backend = GenerativeBackend.googleAI()
                    ).generativeModel("gemini-2.5-flash")

                    val response = model.generateContent(
                        //Prompt que je donne à l'ia et qui répond en fonction de ça
                        "Donne uniquement une liste d'ingrédients ou produits à acheter, sans explication, sans recette, une ligne par élément, pour : $texte"
                    )

                    val texteResultat = response.text

                    result.text = texteResultat ?: "Pas de réponse"

                } catch (e: Exception) {
                    result.text = "Erreur : ${e.message}"
                }
            }
        }
    }
}