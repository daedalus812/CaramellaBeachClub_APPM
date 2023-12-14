package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Ombrelloni : AppCompatActivity() {
    private val numOmbrelli = 9
    private val booleanArray = BooleanArray(numOmbrelli)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umbrella)
    }

    fun processUmbrella(view: View) {
        if (view is Button) {
            val buttonId = view.id
            val umbrella = findViewById<Button>(buttonId)
            val buttonIdName = resources.getResourceEntryName(buttonId)
            val i = Character.getNumericValue(buttonIdName[8].code) - 1
            booleanArray[i] = !booleanArray[i]
            if (booleanArray[i])
                umbrella.text = "Libera"
            else
                umbrella.text = "Occupa"

        }
    }


}