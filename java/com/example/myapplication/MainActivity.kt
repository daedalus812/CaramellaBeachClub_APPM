package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val correctUsername = "Admin"
    private val correctPassword = "Test"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun checkPassword(view: View){
        if (view is Button) {
            val enteredUsername = findViewById<TextView>(R.id.TextUsername).text.toString()
            val enteredPassword = findViewById<TextView>(R.id.TextPassword).text.toString()

            if (enteredUsername == correctUsername && enteredPassword == correctPassword) {
                val intent = Intent(this, SelectionMenu::class.java)
                startActivity(intent)
            } else {
                showToast("Credenziali non valide. Riprova!")
            }
        }
    }

}