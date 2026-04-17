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

class BookBankActivity : AppCompatActivity() {

    private lateinit var booksContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_bank)

        val root = findViewById<View>(R.id.bookBankRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        booksContainer = findViewById(R.id.booksContainer)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        renderBooks()
    }

    private fun renderBooks() {
        booksContainer.removeAllViews()
        LegalBookRepository.getAllBooks().forEach { book ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_legal_book, booksContainer, false)
            card.findViewById<TextView>(R.id.tvBookTitle).text = book.title
            card.findViewById<TextView>(R.id.tvBookCategory).text = book.category
            card.findViewById<TextView>(R.id.tvBookDescription).text = book.description
            card.findViewById<View>(R.id.btnReadBook).setOnClickListener {
                startActivity(
                    Intent(this, BookReaderActivity::class.java)
                        .putExtra("book_id", book.id)
                )
            }
            booksContainer.addView(card)
        }
    }
}
