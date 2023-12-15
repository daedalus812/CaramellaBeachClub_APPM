package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Ombrelloni : AppCompatActivity() {
    private val numOmbrelli = 9
    private val booleanArray = BooleanArray(numOmbrelli)
    private val testo = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umbrella)
        load()
    }


    fun processUmbrella(view: View) {
        if (view is Button) {
            val buttonId = view.id
            val umbrella = findViewById<Button>(buttonId)
            val buttonIdName = resources.getResourceEntryName(buttonId)
            val i = Character.getNumericValue(buttonIdName[8].code) - 1

            val idText = resources.getIdentifier("textView"+(i+1), "id", packageName)
            val textView = findViewById<TextView>(idText)

            val inflater = LayoutInflater.from(this)
            val dialogView = inflater.inflate(R.layout.alert_dialog, null)

            val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
            val editTextSurname = dialogView.findViewById<EditText>(R.id.editTextSurname)
            val spinnerSubscription = dialogView.findViewById<Spinner>(R.id.spinnerSubscription)

            ArrayAdapter.createFromResource(
                this,
                R.array.subscription_types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSubscription.adapter = adapter
            }

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Inserisci i dettagli")
                .setPositiveButton("OK") { dialog, which ->
                    val name = editTextName.text.toString()
                    val surname = editTextSurname.text.toString()
                    val subscriptionType = spinnerSubscription.selectedItem.toString()

                    // Mostra le informazioni nel TextView
                    val infoText = "Nome: $name\nCognome: $surname\nTipo Abbonamento: $subscriptionType"
                    textView.text = infoText

                    booleanArray[i] = !booleanArray[i]
                    if (booleanArray[i]) {
                        umbrella.text = "Libera"
                        textView.visibility = View.VISIBLE
                    } else {
                        umbrella.text = "Occupa"
                        textView.visibility = View.INVISIBLE
                    }
                }
                .setNegativeButton("Annulla") { dialog, which ->
                    // Non fare nulla in caso di annullamento
                }
                .show()
        }
    }

    fun indietro(view: View) {
        save()
        finish()
    }

    fun svuota(view: View) {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        for (i in 0 until numOmbrelli) {
            booleanArray[i] = false
            editor.putBoolean(i.toString(), false)
            val id = resources.getIdentifier("umbrella" + (i + 1) + "Button", "id", packageName)
            val bottone = findViewById<Button>(id)
            bottone.text = "Occupa"
        }
        editor.apply()
    }

    private fun save() {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        for (i in 0 until numOmbrelli)
            editor.putBoolean(i.toString(), booleanArray[i])
        editor.apply()

    }

    private fun load() {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        for (i in 0 until numOmbrelli) {
            booleanArray[i] = prefs.getBoolean(i.toString(), false)
            val id = resources.getIdentifier("umbrella" + (i + 1) + "Button", "id", packageName)
            val bottone = findViewById<Button>(id)
            if (booleanArray[i])
                bottone.text = "Libera"
            else
                bottone.text = "Occupa"
        }
    }
}