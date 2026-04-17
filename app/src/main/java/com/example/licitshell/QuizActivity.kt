package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    private lateinit var quizContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        val root = findViewById<View>(R.id.quizRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        quizContainer = findViewById(R.id.quizContainer)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        renderQuizSets()
    }

    private fun renderQuizSets() {
        quizContainer.removeAllViews()
        QuizData.getQuizzes().forEach { quiz ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_quiz_set, quizContainer, false)
            card.findViewById<TextView>(R.id.tvQuizTitle).text = quiz.title
            card.findViewById<TextView>(R.id.tvQuizDescription).text = quiz.description
            card.findViewById<View>(R.id.btnStartQuiz).setOnClickListener {
                startActivity(
                    Intent(this, QuizQuestionActivity::class.java)
                        .putExtra("quiz_key", quiz.key)
                )
            }
            quizContainer.addView(card)
        }
    }
}
