package com.example.myapplication

import DBHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class MainActivity : AppCompatActivity() {
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inserimento delle credenziali di esempio
        val dbHelper = DBHelper(this)
        dbHelper.inserisciUtente("Admin", "Test")
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun checkPassword(view: View){
        if (view !is Button)
            return

        val usernameInserito = findViewById<TextView>(R.id.TextUsername).text.toString()
        val passwordInserita = findViewById<TextView>(R.id.TextPassword).text.toString()

        val dbHelper = DBHelper(this)
        val passwordSalvata = dbHelper.ottieniPassword(usernameInserito)

        if (passwordSalvata != null && passwordSalvata == passwordInserita) {
            val intent = Intent(this, SelectionMenu::class.java)
            startActivity(intent)
        } else {
            showToast("Credenziali non valide. Riprova!")
            vibrate()
        }
    }

    private fun vibrate() {
        // VibrationEffect per Android 8.0 e successivi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

        //Vibrate per versioni precedenti ad Android 8.0
        } else {
            vibrator.vibrate(100) //
        }
    }
}