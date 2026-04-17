package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var quizSet: QuizSet
    private var currentQuestionIndex = 0
    private val selectedAnswers = mutableListOf<Int?>()

    private lateinit var tvQuizTitle: TextView
    private lateinit var tvQuizProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var btnNext: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_question)

        val root = findViewById<View>(R.id.quizQuestionRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        val quizKey = intent.getStringExtra("quiz_key").orEmpty()
        val loadedQuiz = QuizData.getQuizByKey(quizKey)
        if (loadedQuiz == null) {
            finish()
            return
        }
        quizSet = loadedQuiz
        repeat(quizSet.questions.size) { selectedAnswers.add(null) }

        tvQuizTitle = findViewById(R.id.tvQuizTitle)
        tvQuizProgress = findViewById(R.id.tvQuizProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        optionsGroup = findViewById(R.id.optionsGroup)
        btnNext = findViewById(R.id.btnNextQuestion)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        btnNext.setOnClickListener { onNextClicked() }

        tvQuizTitle.text = quizSet.title
        renderQuestion()
    }

    private fun renderQuestion() {
        val question = quizSet.questions[currentQuestionIndex]
        tvQuizProgress.text = getString(
            R.string.quiz_progress_format,
            currentQuestionIndex + 1,
            quizSet.questions.size
        )
        tvQuestion.text = question.question
        btnNext.text = if (currentQuestionIndex == quizSet.questions.lastIndex) {
            getString(R.string.finish_quiz)
        } else {
            getString(R.string.next_question)
        }

        optionsGroup.removeAllViews()
        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(this).apply {
                id = View.generateViewId()
                text = option
                textSize = 15f
                setTextColor(getColor(R.color.home_text_primary))
                tag = index
            }
            optionsGroup.addView(radioButton)
            if (selectedAnswers[currentQuestionIndex] == index) {
                radioButton.isChecked = true
            }
        }
    }

    private fun onNextClicked() {
        val checkedId = optionsGroup.checkedRadioButtonId
        if (checkedId == -1) {
            Toast.makeText(this, getString(R.string.select_one_option), Toast.LENGTH_SHORT).show()
            return
        }

        val checkedButton = findViewById<RadioButton>(checkedId)
        selectedAnswers[currentQuestionIndex] = checkedButton.tag as Int

        if (currentQuestionIndex < quizSet.questions.lastIndex) {
            currentQuestionIndex += 1
            renderQuestion()
        } else {
            openResult()
        }
    }

    private fun openResult() {
        val correctAnswers = quizSet.questions.countIndexed { index, question ->
            selectedAnswers[index] == question.correctIndex
        }
        val percentage = (correctAnswers * 100) / quizSet.questions.size

        startActivity(
            Intent(this, QuizResultActivity::class.java)
                .putExtra("quiz_title", quizSet.title)
                .putExtra("correct_answers", correctAnswers)
                .putExtra("total_questions", quizSet.questions.size)
                .putExtra("percentage", percentage)
                .putExtra("passed", percentage >= 80)
        )
        finish()
    }

    private inline fun <T> List<T>.countIndexed(predicate: (Int, T) -> Boolean): Int {
        var count = 0
        forEachIndexed { index, item ->
            if (predicate(index, item)) count++
        }
        return count
    }
}
