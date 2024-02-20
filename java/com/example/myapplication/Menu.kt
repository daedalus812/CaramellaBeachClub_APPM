package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menu : AppCompatActivity(), MenuAdapter.OnMenuItemClickListener {
    private lateinit var recyclerView: RecyclerView

    data class MenuItem(var name: String, var price: String, var isAvailable: Boolean = true, var isHeader: Boolean = false)




    private var menuItems = mutableListOf(
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


    fun aggiungiElemento(view: View) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val spinnerHeader = dialogView.findViewById<Spinner>(R.id.spinnerHeader)
        val editTextProductName = dialogView.findViewById<EditText>(R.id.editTextProductName)
        val editTextProductPrice = dialogView.findViewById<EditText>(R.id.editTextProductPrice)

        // Elenco degli header
        val headers = arrayOf("Antipasti", "Primi Piatti", "Secondi Piatti", "Bevande")

        // Adapter Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, headers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHeader.adapter = adapter

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle("Aggiungi Elemento")
        alertDialogBuilder.setPositiveButton("Aggiungi") { dialog, which ->
            val headerPosition = spinnerHeader.selectedItemPosition
            val productName = editTextProductName.text.toString()
            val productPrice = editTextProductPrice.text.toString()


            val nuovoElemento = MenuItem(productName, productPrice, isHeader = false)
            val index = menuItems.indexOfFirst { it.name == headers[headerPosition] }


            menuItems.add(index + 1, nuovoElemento)


            recyclerView.adapter?.notifyItemInserted(index + 1)
        }
        alertDialogBuilder.setNegativeButton("Annulla", null)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }




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


        loadMenuItemsState()

        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            indietro(it)
        }

        val menuAdapter = MenuAdapter(menuItems, this)
        recyclerView.adapter = menuAdapter
    }

    private fun loadMenuItemsState() {
        val sharedPreferences = getSharedPreferences("MyMenuItems", Context.MODE_PRIVATE)
        val savedMenuItems = mutableListOf<MenuItem>()
        var index = 0
        while (true) {
            val name = sharedPreferences.getString("Name_$index", null) ?: break
            val price = sharedPreferences.getString("Price_$index", "") ?: ""
            val isAvailable = sharedPreferences.getBoolean("Available_$index", true)
            val isHeader = sharedPreferences.getBoolean("IsHeader_$index", false)
            savedMenuItems.add(MenuItem(name, price, isAvailable, isHeader))
            index++
        }
        menuItems = savedMenuItems
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

    override fun onRemoveItemClick(position: Int, menuItem: MenuItem) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Rimuovere elemento")
        alertDialogBuilder.setMessage("Sei sicuro di voler rimuovere questo elemento dal menu?")
        alertDialogBuilder.setPositiveButton("Sì") { dialog, which ->
            menuItems.removeAt(position)
            recyclerView.adapter?.notifyItemRemoved(position)
            recyclerView.adapter?.notifyItemRangeChanged(position, menuItems.size)
        }
        alertDialogBuilder.setNegativeButton("No", null)
        alertDialogBuilder.create().show()
    }




    private fun loadAvailabilityState(menuItem: MenuItem) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        menuItem.isAvailable = sharedPreferences.getBoolean(menuItem.name, true)
    }

    override fun onPause() {
        super.onPause()
        saveMenuItemsState()
    }

    private fun saveMenuItemsState() {
        val sharedPreferences = getSharedPreferences("MyMenuItems", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        menuItems.forEachIndexed { index, menuItem ->
            editor.putString("Name_$index", menuItem.name)
            editor.putString("Price_$index", menuItem.price)
            editor.putBoolean("Available_$index", menuItem.isAvailable)
            editor.putBoolean("IsHeader_$index", menuItem.isHeader)
        }
        editor.apply()
    }



}
