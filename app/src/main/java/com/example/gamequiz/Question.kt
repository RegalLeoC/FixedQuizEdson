package com.example.gamequiz

data class Question (
    val text: String,
    val correctAnswer: String,
    val wrongAnswers: List<String>
){
    var answered: Boolean = false
}