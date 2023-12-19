package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menu : AppCompatActivity(), MenuAdapter.OnMenuItemClickListener {
    private lateinit var recyclerView: RecyclerView
    data class MenuItem(val name: String, val price: String, var isAvailable: Boolean = true, val isHeader: Boolean = false)

    private val menuItems = listOf(
        MenuItem("Antipasti", "", isHeader = true),
        MenuItem("Antipasto dello Ionio", "€12.00"),
        MenuItem("Fresco di Mare", "€9.00"),
        MenuItem("Primi Piatti", "", isHeader = true),
        MenuItem("Spaghetti Cozze e Vongole", "€13.00"),
        MenuItem("Spaghetti agli Scampi", "€16.00"),
        MenuItem("Secondi Piatti", "", isHeader = true),
        MenuItem("Trancio di Tonno Scottato", "€12.00"),
        MenuItem("Spada alla Griglia", "€18.00"),
        MenuItem("Bevande", "", isHeader = true),
        MenuItem("Heineken", "€2.00"),
        MenuItem("Corona", "€3.00"),
        MenuItem("Peroni", "€2.00"),
        MenuItem("Acqua 1LT Naturale", "€2.00"),
        MenuItem("Acqua 0.5LT Naturale", "€1.00"),
        MenuItem("Acqua 1LT Frizzante", "€2.00"),
        MenuItem("Acqua 0.5LT Frizzante", "€1.00")
    )


    private fun saveAvailabilityState(menuItem: MenuItem) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(menuItem.name, menuItem.isAvailable)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            indietro(it)
        }


        for (menuItem in menuItems) {
            loadAvailabilityState(menuItem)
        }

        val menuAdapter = MenuAdapter(menuItems, this)
        recyclerView.adapter = menuAdapter
    }

    fun indietro(view: View) {
        finish()
    }

    override fun onMenuItemClick(position: Int, menuItem: MenuItem) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Scegli disponibilità")

        val options = arrayOf("Disponibile", "Non disponibile")

        alertDialogBuilder.setItems(options) { _, which ->

            menuItem.isAvailable = which == 0

            saveAvailabilityState(menuItem)

            recyclerView.adapter?.notifyItemChanged(position)
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun loadAvailabilityState(menuItem: MenuItem) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        menuItem.isAvailable = sharedPreferences.getBoolean(menuItem.name, true)
    }

}
