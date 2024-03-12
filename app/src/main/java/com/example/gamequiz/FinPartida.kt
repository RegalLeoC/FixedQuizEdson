package com.example.gamequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        deductionTextView.text = "Deduction: $deduction points"
        totalScoreTextView.text = "Total Score: $totalScore points"
        finalResultTextView.text = "Final Result: $finalResult"
        difficultyMultiplierTextView.text = "Difficulty Multiplier: $difficultyMultiplier"

        validationImgAndResult(finalResult)

    }


    private fun validationImgAndResult(result : Double) {
        val imageF: ImageView = findViewById(R.id.logoImageResult)

        if (result in 100.0..200.0)
            imageF.setImageResource(R.drawable.perritollorn)
        if (result in 201.0..400.0)
            imageF.setImageResource(R.drawable.knucles)
        if (result in 401.0..700.0)
            imageF.setImageResource(R.drawable.nah_id_win)
        else
            imageF.setImageResource(R.drawable.mirada100)


    }
}