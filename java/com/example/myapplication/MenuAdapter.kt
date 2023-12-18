package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menuItems: List<Menu.MenuItem>, private val clickListener: OnMenuItemClickListener) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    interface OnMenuItemClickListener {
        fun onMenuItemClick(position: Int, menuItem: Menu.MenuItem)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val disponibilityTextView: TextView = itemView.findViewById(R.id.disponibilityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.nameTextView.text = menuItem.name
        holder.priceTextView.text = menuItem.price
        holder.disponibilityTextView.text = if (menuItem.isAvailable) "Disponibile" else "Non disponibile"

        // Rimuovi il gestore del clic dal onBindViewHolder
        holder.itemView.setOnClickListener(null)

        // Aggiungi il gestore del clic per mostrare l'AlertDialog
        holder.itemView.setOnClickListener {
            clickListener.onMenuItemClick(position, menuItem)
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}

