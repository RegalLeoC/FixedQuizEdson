package com.example.gamequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OpcionesJuego : AppCompatActivity() {

    private lateinit var buttonVolver :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_juego)


        buttonVolver = findViewById(R.id.InicioButton)


        buttonVolver.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            finish()


        }
    }


}