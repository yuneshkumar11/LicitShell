package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_reader)

        val root = findViewById<View>(R.id.bookReaderRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        val bookId = intent.getIntExtra("book_id", -1)
        val book = LegalBookRepository.getBookById(bookId)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        if (book != null) {
            findViewById<TextView>(R.id.tvReaderTitle).text = book.title
            findViewById<TextView>(R.id.tvReaderCategory).text = book.category
            findViewById<TextView>(R.id.tvReaderDescription).text = book.description
            findViewById<TextView>(R.id.tvReaderContent).text = book.content
            findViewById<TextView>(R.id.tvReaderSource).text =
                getString(R.string.book_source_format, book.sourceName)
            findViewById<View>(R.id.btnOpenBookSource).setOnClickListener {
                startActivity(
                    Intent(this, BookWebViewActivity::class.java)
                        .putExtra("web_title", book.title)
                        .putExtra("web_url", book.sourceUrl)
                )
            }
        } else {
            findViewById<TextView>(R.id.tvReaderTitle).text = getString(R.string.book_not_found)
            findViewById<TextView>(R.id.tvReaderCategory).text = ""
            findViewById<TextView>(R.id.tvReaderDescription).text = getString(R.string.book_not_found_desc)
            findViewById<TextView>(R.id.tvReaderContent).text = ""
            findViewById<TextView>(R.id.tvReaderSource).text = ""
            findViewById<View>(R.id.btnOpenBookSource).visibility = View.GONE
        }
    }
}
