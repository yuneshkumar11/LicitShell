package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_result)

        val root = findViewById<View>(R.id.quizResultRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        val quizTitle = intent.getStringExtra("quiz_title").orEmpty()
        val correctAnswers = intent.getIntExtra("correct_answers", 0)
        val totalQuestions = intent.getIntExtra("total_questions", 10)
        val percentage = intent.getIntExtra("percentage", 0)
        val passed = intent.getBooleanExtra("passed", false)

        if (passed) {
            UserProgressStore.addPassedQuiz(this, quizTitle)
        }

        findViewById<TextView>(R.id.tvResultTitle).text = quizTitle
        findViewById<TextView>(R.id.tvResultStatus).text =
            getString(if (passed) R.string.quiz_passed else R.string.quiz_failed)
        findViewById<TextView>(R.id.tvResultMarks).text =
            getString(R.string.quiz_report_format, correctAnswers, totalQuestions, percentage)
        findViewById<TextView>(R.id.tvPassingRule).text =
            getString(R.string.quiz_passing_rule)

        findViewById<View>(R.id.btnBackToQuizHome).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.btnBackToHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
