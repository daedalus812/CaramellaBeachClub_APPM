package com.example.myapplication

import DBHelperOmbrelloni
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Ombrelloni : AppCompatActivity() {
    private lateinit var dbHelper: DBHelperOmbrelloni
    private val numOmbrelli = 9
    private val booleanArray = BooleanArray(numOmbrelli)
    private val testo = Array<String>(numOmbrelli) { i -> "\n\n" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umbrella)
        dbHelper = DBHelperOmbrelloni(this)
        load()
        addExampleOmbrelloni()
    }

    fun processUmbrella(view: View) {
        if (view !is Button)
            return

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

        if (!booleanArray[i]) {
            AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Inserisci i dettagli")
                .setPositiveButton("OK") { dialog, which ->
                    val name = editTextName.text.toString()
                    val surname = editTextSurname.text.toString()
                    val subscriptionType = spinnerSubscription.selectedItem.toString()

                    val infoText = "$name\n$surname\n$subscriptionType"
                    testo[i] = infoText
                    textView.text = infoText
                    textView.visibility = View.VISIBLE
                    umbrella.text = "Libera"
                    booleanArray[i] = !booleanArray[i]
                    dbHelper.addOrUpdateOmbrellone(i, true, infoText)
                }
                .setNegativeButton("Annulla") { dialog, which ->
                }
                .show()

        } else {
            umbrella.text = "Occupa"
            textView.visibility = View.INVISIBLE
            testo[i] = "\n\n"
            dbHelper.addOrUpdateOmbrellone(i, false, "")
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
            editor.putString("textView"+(i+1), "")

            val id = resources.getIdentifier("umbrella" + (i + 1) + "Button", "id", packageName)
            val bottone = findViewById<Button>(id)
            bottone.text = "Occupa"

            val idTextView = resources.getIdentifier("textView"+ (i + 1), "id", packageName)
            val textView = findViewById<TextView>(idTextView)
            textView.text = "\n\n"
            textView.visibility = View.VISIBLE

            dbHelper.addOrUpdateOmbrellone(i, false, "")
        }
        editor.apply()
    }

    private fun save() {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        for (i in 0 until numOmbrelli) {
            editor.putBoolean(i.toString(), booleanArray[i])
            editor.putString("textView"+(i+1), testo[i])
            dbHelper.addOrUpdateOmbrellone(i, booleanArray[i], testo[i])
        }
        editor.apply()

    }

    private fun load() {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        for (i in 0 until numOmbrelli) {
            booleanArray[i] = prefs.getBoolean(i.toString(), false)
            testo[i] = prefs.getString("textView"+(i+1),"").toString()

            if (!booleanArray[i])
                continue

            val idButton = resources.getIdentifier("umbrella" + (i + 1) + "Button", "id", packageName)
            val bottone = findViewById<Button>(idButton)
            bottone.text = "Libera"

            val idTextView = resources.getIdentifier("textView"+ (i + 1), "id", packageName)
            val textView = findViewById<TextView>(idTextView)
            textView.text = testo[i]
            textView.visibility = View.VISIBLE

            dbHelper.addOrUpdateOmbrellone(i, true, testo[i])
        }
    }

    private fun addExampleOmbrelloni() {
        for (i in 0 until numOmbrelli) {
            val isOccupied = i % 2 == 0 // Occupa ogni secondo ombrellone di esempio
            val info = if (isOccupied) "Esempio Occupato" else "" // Informazioni di esempio
            dbHelper.addOrUpdateOmbrellone(i, isOccupied, info)
            booleanArray[i] = isOccupied
            testo[i] = info
        }
    }
}
