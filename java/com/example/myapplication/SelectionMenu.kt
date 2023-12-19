package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.VideoView


class SelectionMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_menu) // Muovi questa chiamata qui

        // Assicurati di usare l'ID corretto
        val backgroundImageView: ImageView = findViewById(R.id.backgroundImageView)

        // Verifica se l'oggetto non è nullo prima di chiamare il metodo setImageResource

        backgroundImageView.setImageResource(R.drawable.background_image)


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
