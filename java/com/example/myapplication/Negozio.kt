package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Negozio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
    }

    fun provide(view: View){
        if (view !is Button)
            return

        val buttonId = view.id

        val buttonIdName = resources.getResourceEntryName(buttonId)
        val numero = Character.getNumericValue(buttonIdName[6].code)

        Toast.makeText(this, "Fornito Oggetto $numero", Toast.LENGTH_SHORT).show()


    }
}