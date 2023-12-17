package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menu : AppCompatActivity() {
    data class MenuItem(val name: String, val price: String)

    private val menuItems = listOf(
        MenuItem("Antipasto 1", "€10.00"),
        MenuItem("Antipasto 2", "€12.00"),
        MenuItem("Primo Piatto 1", "€8.00"),
        MenuItem("Primo Piatto 2", "€9.00"),
        MenuItem("Secondo Piatto 1", "€15.00"),
        MenuItem("Secondo Piatto 2", "€18.00"),
        MenuItem("Bevanda 1", "€5.00"),
        MenuItem("Bevanda 2", "€3.00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MenuAdapter(menuItems)
    }
}
