package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuItems: List<Menu.MenuItem>,
    private val onMenuItemClickListener: OnMenuItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnMenuItemClickListener {
        fun onMenuItemClick(position: Int, menuItem: Menu.MenuItem)
    }

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_header_layout, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_layout, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.headerTextView.text = menuItems[position].name
        } else if (holder is ItemViewHolder) {
            val menuItem = menuItems[position]
            holder.nameTextView.text = menuItem.name
            holder.priceTextView.text = menuItem.price
            holder.availableTextView.text = if (menuItem.isAvailable) "Disponibile" else "Non disponibile"
            holder.itemView.setOnClickListener { onMenuItemClickListener.onMenuItemClick(position, menuItem) }
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (menuItems[position].isHeader) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView: TextView = itemView.findViewById(R.id.headerTextView)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val availableTextView: TextView = itemView.findViewById(R.id.disponibilityTextView)
    }


}

