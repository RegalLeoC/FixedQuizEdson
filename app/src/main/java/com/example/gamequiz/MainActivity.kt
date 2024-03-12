package com.example.gamequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val difficulty = arrayOf("Fácil", "Normal", "Difícil" )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.difficultySpinner)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, difficulty)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

        val jugarButton = findViewById<Button>(R.id.jugarButton)
        jugarButton.setOnClickListener {
            val intent = Intent(this, Juego::class.java)
            intent.putExtra("difficulty", difficulty[spinner.selectedItemPosition])
            startActivity(intent)
            finish()
        }

        val opcionesbutton = findViewById<Button>(R.id.opcionesButton)
        opcionesbutton.setOnClickListener{
            val intent = Intent(this, OpcionesJuego::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        Toast.makeText(applicationContext, "Dificultad: " + difficulty[position], Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        {

        }
    }




}