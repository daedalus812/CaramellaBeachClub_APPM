package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SelectionMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_menu)
        val menu = findViewById<ImageButton>(R.id.menuButton)
        val umbrella = findViewById<ImageButton>(R.id.umbrellaButton)
        val shop = findViewById<ImageButton>(R.id.shopButton)
        val back = findViewById<ImageButton>(R.id.backButton)


        menu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        umbrella.setOnClickListener {
            val intent = Intent(this, Ombrelloni::class.java)
            startActivity(intent)
        }
        shop.setOnClickListener {
            val intent = Intent(this, Negozio::class.java)
            startActivity(intent)
        }
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}