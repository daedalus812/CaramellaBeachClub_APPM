package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
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
        Toast.makeText(this, "Richiesta di Fornitura Inviata", Toast.LENGTH_SHORT).show()


    }
}