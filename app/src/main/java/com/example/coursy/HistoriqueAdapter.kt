package com.example.coursy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursy.databinding.HistoriqueItemBinding

class HistoriqueAdapter(private val liste: List<HistoriqueItem> , private val onClick: (HistoriqueItem) -> Unit) :
    RecyclerView.Adapter<HistoriqueAdapter.ViewHolder>() {

    class ViewHolder(val binding: HistoriqueItemBinding)
        : RecyclerView.ViewHolder(binding.root)
    // Crée une ligne à partir du xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Permet de gérer une liste d'historique
        val binding = HistoriqueItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // Retourne une ligne
        return ViewHolder(binding)
    }
    //  C'est le nombre d'article à afficher , la taille de la liste
    override fun getItemCount(): Int = liste.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Je déclare un item comme élément à la position d'une liste
        val item = liste[position]

        holder.binding.textDate.text = item.date
        holder.binding.textTotal.text = "Total : ${item.total} €"

        // Pour le clic
        holder.binding.root.setOnClickListener {
            onClick(item)
        }

    }
}
