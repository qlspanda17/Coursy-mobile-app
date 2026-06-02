package com.example.coursy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursy.databinding.ArticleItemBinding

class ArticleAdapter
    (
    private val liste: MutableList<Article>,
    // Permet de détecter un appui long
    // Unit pour ne rien retourner
    private val onItemLongClick: (Int) -> Unit
)

    : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    // Un viewholder représente une ligne dans la liste
        // biding permet d'accéder aux éléments du xml
    class ViewHolder(val binding: ArticleItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    // Je crée une ligne vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ArticleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        //  Je renvoie la ligne crée
        return ViewHolder(binding)

    }
    //  C'est le nombre d'article à afficher , la taille de la liste
    override fun getItemCount(): Int = liste.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Je récupère l'article à afficher
        val article = liste[position]

        // Je met le nom dans le texteView
        // biding pour un accès direct à l'id nomArticle de mon xml
        holder.binding.nomArticle.text = article.nom

        holder.binding.prixArticle.text =
            "${article.prix}€ - ${article.quantite} ${article.unite}"

        //
        holder.binding.root.setOnLongClickListener {


            onItemLongClick(position)

            true
        }

    }
}