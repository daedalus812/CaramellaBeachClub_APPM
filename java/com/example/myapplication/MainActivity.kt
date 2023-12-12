package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textUsername = findViewById<TextView>(R.id.TextUsername)
        val textPassword = findViewById<TextView>(R.id.TextPassword)
        val loginButton = findViewById<Button>(R.id.LoginButton)

        loginButton.setOnClickListener {
            val enteredUsername = textUsername.text.toString()
            val enteredPassword = textPassword.text.toString()

            val correctUsername = "Admin"
            val correctPassword = "Test"

            if (enteredUsername == correctUsername && enteredPassword == correctPassword) {
                showToast("Credenziali Corrette!")
                val intent = Intent(this, SelectionMenu::class.java)
                startActivity(intent)
            } else {
                showToast("Credenziali non valide. Riprova!")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}