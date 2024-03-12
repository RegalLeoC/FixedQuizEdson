package com.example.gamequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class FinPartida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fin_partida)


        val deduction = intent.getIntExtra("Deduction", 0)
        val totalScore = intent.getIntExtra("totalScore", 0)
        val finalResult = intent.getDoubleExtra("FinalResult", 0.0)
        val difficultyMultiplier = intent.getDoubleExtra("difficultyMultiplier", 1.0)


        val deductionTextView: TextView = findViewById(R.id.textViewHist)
        val totalScoreTextView: TextView = findViewById(R.id.textViewQuest)
        val finalResultTextView: TextView = findViewById(R.id.textViewResultFin)
        val difficultyMultiplierTextView: TextView = findViewById(R.id.textViewBonf)

        deductionTextView.text = "$deduction points"
        totalScoreTextView.text = "$totalScore points"
        finalResultTextView.text = "$finalResult"
        difficultyMultiplierTextView.text = "$difficultyMultiplier"

        validationImgAndResult(finalResult)

        val buttonVolver: Button = findViewById(R.id.regresarInicioButton)

        buttonVolver.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            finish()

        }



    }


    private fun validationImgAndResult(result : Double) {
        val imageF: ImageView = findViewById(R.id.logoImageResult)

        if (result in 0.0..200.0)
            imageF.setImageResource(R.drawable.mirada100)
        if (result in 201.0..400.0)
            imageF.setImageResource(R.drawable.perritollorn)
        if (result in 401.0..700.0)
            imageF.setImageResource(R.drawable.knucles)
        if(result >= 701.0)
            imageF.setImageResource(R.drawable.nah_id_win)


    }
}