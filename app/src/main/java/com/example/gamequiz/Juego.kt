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
    private var questionOptions: MutableMap<Int, List<String>> = mutableMapOf() // List of all options we generate initially per question
    private var enabledWrongOptions: MutableMap<Int, MutableList<String>> = mutableMapOf() // List of wrong options that are enabled
    private var disabledWrongOptions: MutableMap<Int, List<String>> = mutableMapOf() // List of wrong options that were disabled by using hint
    private var answeredQuestions: MutableMap<Int, Boolean> = mutableMapOf() // Questions answered through manual selection
    private var answeredQuestionsHint:  MutableMap<Int, Boolean> = mutableMapOf() // Questions answered through hint
    private var userSelection: MutableMap<Int, String?> = mutableMapOf() //Right answers selected by the user
    private var hintSelection: MutableMap<Int, String?> = mutableMapOf() //Right answers selected by the hint

    //Question variables
    private var questionIndex: Int = 0;
    private lateinit var topics: Array<Topics>
    private lateinit var questions: List<Question>

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


        //Layout
        buttonContainer = findViewById(R.id.buttonContainer)
        questionTextView = findViewById(R.id.questionTextView)
        topicImageView = findViewById(R.id.topicImageView)
        questionNumberTextView = findViewById(R.id.questionNumberTextView)
        hintTextView = findViewById(R.id.hintTextView)
        hintButton = findViewById(R.id.hintButton)

        //Saving the topics
        topics = Topics.values()

        //Initializing
        selectRandomQuestions()

        //Show questions
        updateQuestion()

        //Initialize maps based on amount of questions (that were created in selectrandomquestions)
        for(i in 0 until questions.size){
            answeredQuestions[i] = false; // None of the questions are answered yet
            answeredQuestionsHint[i] = false; // None of the questions are answered yet
            userSelection[i] = null; // Putting a null for it not to be empty
            hintSelection[i] = null; // Putting a null for it not to be empty
        }

        //Exploration
        findViewById<Button>(R.id.nextButton).setOnClickListener {
            nextQuestion()
        }

        findViewById<Button>(R.id.prevButton).setOnClickListener {
            previousQuestion()
        }

        //Use hint
        hintButton.setOnClickListener {
            useHint()
        }


    }

    private fun selectRandomQuestions(){
        val allQuestions = topics.flatMap {it.questions}.toMutableList()
        questions = mutableListOf()

        repeat(10){
            val randomQuestion = allQuestions.random();
            (questions as MutableList<Question>).add(randomQuestion)
            allQuestions.remove(randomQuestion)
        }

    }

    private fun updateQuestion() {
        //Establish which question are we in
        val currentQuestion = questions[questionIndex];

        //Look which topics for associated pic of the question
        //Agarra la pregunta actual y revisa en que tema va
        // Se tiene que poner un caso nulo para poder usar el topicImageView
        val currentTopic = topics.find{it.questions.contains(currentQuestion)} ?: Topics.MATHEMATICS

       //Mostrar imagen del tema
        topicImageView.setImageResource(currentTopic.imageResourceId)

        //Mostrar pregunta
        questionTextView.text = currentQuestion.text

        // Mostrar en que numero de pregunta estamos
        val questionNumberText = "${questionIndex + 1}/${questions.size}"
        questionNumberTextView.text = questionNumberText


        // Si es la primera ves que se visita la pregunta, se generan opciones para escoger
        if(questionOptions[questionIndex] == null){
            val options = generateQuestionsOptions(currentQuestion)
            questionOptions[questionIndex] = options;
        }

        // Creamos los botones segun las opciones que arriba creamos
        createButtons(questionOptions[questionIndex]!!)

        // Si ya hemos deshabilitado opciones con hint ponerlo
        //val discardedOptions = disabledWrongOptions[questionIndex];
        //discardedOptions?.let{
        //    discardHint(it)
        //}

        // Aqui si contestamos con el hint despues que queden dos
        //val hintAnswered = hintSelection[questionIndex];
        //hintAnswered?.let {
        //    discardHintCorrect(it)
        //}

    }


    //Aqui generamos las opciones segun la cantidad (dificultad)
    private fun generateQuestionsOptions(question: Question): List<String> {
        val options = mutableListOf<String>()
        options.add(question.correctAnswer)

        val numWrongAnswers = when (difficulty) {
            "Fácil" -> 1
            "Normal" -> 2
            "Difícil" -> 3
            else -> 1
        }

        val wrongAnswers = question.wrongAnswers.shuffled().take(numWrongAnswers)
        options.addAll(wrongAnswers)

        enabledWrongOptions[questionIndex] = wrongAnswers.toMutableList()

        return options.shuffled()
    }

    // Aqui mostramos las opciones segun la pregunta adecuada
    private fun createButtons(options: List<String>){
        buttonContainer.removeAllViews()


        val isAnswered = answeredQuestions[questionIndex]?: false
        val isAnsweredHint = answeredQuestionsHint[questionIndex]?: false


        for (option in options){
            val button = Button(this)
            button.text = option



            val isSelected = userSelection[questionIndex] == option;

            //Checa si esta contestado
            // Si contestaste bien pues se pone verde, pero si contestaste mal, se pinta el rojo y se pone el verde mostrandote cual era el correcto
            if (isAnswered) {
                val correctAnswer = questions[questionIndex].correctAnswer
                if (option == correctAnswer) {
                    button.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                } else if (isSelected) {
                    button.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                }





                disableButtons()

            }


            //Aqui poner la de navagacion de regreso para el hint (similar a lo dde arriba)
            val hintedButton = disabledWrongOptions[questionIndex]?.contains(option) ?: false

            if(isAnsweredHint){
                val correctAnswer = questions[questionIndex].correctAnswer

                if(option == correctAnswer) {
                    button.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                }

                //!!!!!!!!! No sirve porque deshabilita todos los hints (pa eso sirve la funcion exclusiva que revise los disabled)
                if(hintedButton){
                    button.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light))
                }

                //!!!!!!No deshabilita todos. Deja el ultimo habilitado
                disableButtons()

            }


            // Aqui es cuanddo no haz contestado y se guarda en la lista de contestado manual

            button.setOnClickListener {
                if (!isAnswered) {
                    val correctAnswer = questions[questionIndex].correctAnswer
                    if (option == correctAnswer) {
                        button.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                        correctAnswers++ // Increment totalCorrectAnswers when the correct answer is selected

                        //Add one more hint if in hint streak
                        if (hintStreak == 2){
                            hintsAvailable++
                            hintTextView.text = hintsAvailable.toString()
                            hintStreak = 0
                        } else {
                            hintStreak++
                        }



                    } else {
                        button.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                    }

                    answeredQuestions[questionIndex] = true
                    userSelection[questionIndex] = option
                    disableButtons() // Disable buttons after answering

                    //Checar si ya hay que terminar el juego
                    endGame()
                }
            }

            buttonContainer.addView(button)

        }


    }

    // Despues dded contestar se deshabilitan todos los botones para preevenir que el usuario conteste otra vez
    private fun disableButtons() {

        for (i in 0 until buttonContainer.childCount) {
            val child = buttonContainer.getChildAt(i)
            if (child is Button) {

                child.isEnabled = false

            }
        }
    }


    private fun useHint() {

        //check if we have hints remaining
        if(hintsAvailable > 0) {

            //Update variables
            hintsAvailable--
            hintStreak = 0
            hintsUsed++

            // Update text
            hintTextView.text = hintsAvailable.toString()

            //Se inicilizan las variables para apuntar a la prgunta actual y sus opciones incorrectas
            val currentQuestion = questions[questionIndex]
            val enabledOptions = enabledWrongOptions[questionIndex]

            // Se selecciona al azar una opcion para deshabilitar con la pista
            if((enabledOptions?.size ?:0) > 1){
                val randomIndex = (0 until enabledOptions!!.size).random()
                val optionToDisable = enabledOptions[randomIndex]

                // Remove from enabled options and add it to disabled options
                enabledWrongOptions[questionIndex]?.remove(optionToDisable)
                disabledWrongOptions[questionIndex] = (disabledWrongOptions[questionIndex]?: emptyList()) + optionToDisable

                disableOption(optionToDisable)

            } else {
                // Si solo queda una opcion mala la pregunta se contesta sola
                val correctAnswer = currentQuestion.correctAnswer
                for(i in 0 until buttonContainer.childCount){
                    val child = buttonContainer.getChildAt(i)
                    if (child is Button) {
                        val option = child.text.toString()
                        if (option != correctAnswer && enabledOptions!!.contains(option)) {
                            disabledWrongOptions[questionIndex] = (disabledWrongOptions[questionIndex]?: emptyList()) + option
                            disableOption(option)
                        } else if (option == correctAnswer) {
                            correctAnswers++
                            // Necesito contar estos mas el userSelection para terminarlo
                            hintSelection[questionIndex] = option
                            answeredQuestionsHint[questionIndex] = true
                            child.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                            //Igual deshabilita el hint button????
                            disableButtons()

                            //Checar si ya hay que terminar el juego
                            endGame()
                        }

                    }

                }


            }


        }


    }


    private fun disableOption(option: String) {
        for (i in 0 until buttonContainer.childCount) {
            val child = buttonContainer.getChildAt(i)
            if (child is Button && child.text.toString() == option) {
                child.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light))
                child.isEnabled = false
            }
        }
    }


    // Navigation through buttons

    private fun nextQuestion() {

        questionIndex = (questionIndex + 1) % questions.size
        updateQuestion()
    }

    private fun previousQuestion() {
        questionIndex = (questionIndex - 1 + questions.size) % questions.size
        updateQuestion()
    }

    private fun endGame() {
        val totalAnswers = hintSelection.count { it.value != null } + userSelection.count { it.value != null }
        if (totalAnswers == 10) {
            viewResults()
        }
    }

    private fun viewResults() {
        // Calculate the final result based on the specified formula
        difficultyMultiplier = when (difficulty) {
            "Fácil" -> 1.0
            "Normal" -> 1.25
            "Difícil" -> 1.5
            else -> 1.0
        }

        // Deduct points for hints
        val deduction = hintsUsed * 25  // Deduct 25 points for each hint used
        val totalpoints = correctAnswers * 100
        val finalResult = ((totalpoints - deduction) * difficultyMultiplier)


        // Pass the results to the FinPartida activity
        val intent = Intent(this, FinPartida::class.java)
        intent.putExtra("Deduction", deduction)
        intent.putExtra("totalScore", totalpoints)
        intent.putExtra("FinalResult", finalResult)
        intent.putExtra("difficultyMultiplier", difficultyMultiplier)

        startActivity(intent)

    }




}