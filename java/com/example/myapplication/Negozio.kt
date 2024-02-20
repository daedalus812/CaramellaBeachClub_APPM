package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class Negozio : AppCompatActivity() {
    private lateinit var vibrator: Vibrator // Dichiarazione del vibratore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator // Inizializzazione del vibratore
    }

    fun provide(view: View){
        if (view !is Button)
            return
        Toast.makeText(this, "Richiesta di Fornitura Inviata", Toast.LENGTH_SHORT).show()
        vibrate()
    }

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100) //
        }
    }
}