package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Ombrelloni : AppCompatActivity() {
    private val numOmbrelli = 9
    private val booleanArray = BooleanArray(numOmbrelli)

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
            booleanArray[i] = !booleanArray[i]
            if (booleanArray[i]) {
                umbrella.text = "Libera"

                //alertDialog

                val idText = resources.getIdentifier("TextView" + i, "id", packageName)
                val textView = findViewById<Button>(idText)
                textView.visibility = View.VISIBLE
            }
            else
                umbrella.text = "Occupa"

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