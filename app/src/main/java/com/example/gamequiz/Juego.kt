package com.example.gamequiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Juego : AppCompatActivity() {

    //Layout

    private lateinit var buttonContainer: LinearLayout
    private lateinit var questionTextView: TextView
    private lateinit var topicImageView: ImageView
    private lateinit var questionNumberTextView: TextView
    private lateinit var hintTextView: TextView
    private lateinit var hintButton: Button


    // Maps (dictionaries) to follow
    private var questionOptions: MutableMap<Int, List<String>> = mutableMapOf()
    private var enabledWrongOptions: MutableMap<Int, List<String>> = mutableMapOf()
    private var disabledWrongOptions: MutableMap<Int, List<String>> = mutableMapOf()
    private var answeredQuestions: MutableMap<Int, List<String>> = mutableMapOf()



    //Question variables
    private var questionIndex: Int = 0;

    // Hint variables
    private var hintsAvailable: Int = 5;
    private var hintStreak: Int = 0;


    // Score variables
    private var hintsUsed: Int = 0;
    private var finalScore: Int = 0;
    private var correctAnswers: Int = 0;
    private var difficultyMultiplier: Double = 1.0

    // Getting difficulty

    private val difficulty: String? by lazy {
        intent.getStringExtra("difficulty")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)
    }
}