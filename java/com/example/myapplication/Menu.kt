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
    private lateinit var recyclerView: RecyclerView  // Dichiarazione di recyclerView
    data class MenuItem(val name: String, val price: String, var isAvailable: Boolean = true)
    private val menuItems = listOf(
        MenuItem("Antipasto 1", "€10.00"),
        MenuItem("Antipasto 2", "€12.00"),
        MenuItem("Primo Piatto 1", "€8.00"),
        MenuItem("Primo Piatto 2", "€9.00"),
        MenuItem("Secondo Piatto 1", "€15.00"),
        MenuItem("Secondo Piatto 2", "€18.00"),
        MenuItem("Bevanda 1", "€5.00"),
        MenuItem("Bevanda 2", "€3.00")
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
            // Carica lo stato della disponibilità dalle SharedPreferences
            loadAvailabilityState(menuItem)
        }

        val menuAdapter = MenuAdapter(menuItems, this)
        recyclerView.adapter = menuAdapter
    }

    fun indietro(view: View) {
        finish()
    }



    override fun onMenuItemClick(position: Int, menuItem: MenuItem) {
        // Costruisci l'AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Scegli disponibilità")

        val options = arrayOf("Disponibile", "Non disponibile")

        alertDialogBuilder.setItems(options) { _, which ->
            // Aggiorna lo stato del piatto solo dopo la scelta nell'AlertDialog
            menuItem.isAvailable = which == 0

            // Salva lo stato della disponibilità nelle SharedPreferences
            saveAvailabilityState(menuItem)

            // Notifica all'Adapter che i dati sono stati modificati
            recyclerView.adapter?.notifyItemChanged(position)
        }

        // Crea e mostra l'AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    // Aggiungi questa funzione nella tua classe Menu
    private fun loadAvailabilityState(menuItem: MenuItem) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        menuItem.isAvailable = sharedPreferences.getBoolean(menuItem.name, true)
    }



}
