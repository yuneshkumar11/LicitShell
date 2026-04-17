package com.example.licitshell

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LibraryActivity : AppCompatActivity() {

    private lateinit var topicsContainer: LinearLayout
    private lateinit var videosContainer: LinearLayout
    private lateinit var tvNoTopics: TextView
    private lateinit var etSearchTopics: EditText
    private lateinit var btnCategoryAll: AppCompatButton
    private lateinit var btnCategorySafety: AppCompatButton
    private lateinit var btnCategoryEducation: AppCompatButton
    private lateinit var btnCategoryJustice: AppCompatButton

    private var selectedCategory: String = "All"
    private var currentSearchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_library)

        val root = findViewById<View>(R.id.libraryRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        topicsContainer = findViewById(R.id.libraryTopicsContainer)
        videosContainer = findViewById(R.id.libraryVideosContainer)
        tvNoTopics = findViewById(R.id.tvNoLibraryTopics)
        etSearchTopics = findViewById(R.id.etSearchTopics)
        btnCategoryAll = findViewById(R.id.btnCategoryAll)
        btnCategorySafety = findViewById(R.id.btnCategorySafety)
        btnCategoryEducation = findViewById(R.id.btnCategoryEducation)
        btnCategoryJustice = findViewById(R.id.btnCategoryJustice)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        btnCategoryAll.setOnClickListener { selectCategory("All") }
        btnCategorySafety.setOnClickListener { selectCategory("Safety") }
        btnCategoryEducation.setOnClickListener { selectCategory("Education") }
        btnCategoryJustice.setOnClickListener { selectCategory("Justice") }
        etSearchTopics.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchQuery = s?.toString().orEmpty()
                renderTopics()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        updateCategoryButtons()
        renderTopics()
        renderVideos()
    }

    private fun renderTopics() {
        topicsContainer.removeAllViews()
        val query = currentSearchQuery.trim()
        val filteredTopics = LibraryData.getTopics().filter { topic ->
            val categoryMatch = selectedCategory == "All" || topic.category == selectedCategory
            val queryMatch = query.isEmpty() ||
                topic.title.contains(query, ignoreCase = true) ||
                topic.subtitle.contains(query, ignoreCase = true) ||
                topic.content.contains(query, ignoreCase = true)
            categoryMatch && queryMatch
        }

        tvNoTopics.visibility = if (filteredTopics.isEmpty()) View.VISIBLE else View.GONE

        filteredTopics.forEach { topic ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_library_topic, topicsContainer, false)
            card.findViewById<TextView>(R.id.tvLibraryTopicCategory).text = topic.category
            card.findViewById<TextView>(R.id.tvLibraryTopicTitle).text = topic.title
            card.findViewById<TextView>(R.id.tvLibraryTopicSubtitle).text = topic.subtitle
            card.findViewById<View>(R.id.btnReadTopic).setOnClickListener {
                startActivity(
                    Intent(this, LibraryTopicActivity::class.java)
                        .putExtra("topic_id", topic.id)
                )
            }
            topicsContainer.addView(card)
        }
    }

    private fun renderVideos() {
        videosContainer.removeAllViews()
        LibraryData.getVideos().forEach { video ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_library_video, videosContainer, false)
            card.findViewById<TextView>(R.id.tvLibraryVideoTitle).text = video.title
            card.findViewById<TextView>(R.id.tvLibraryVideoSource).text = video.source
            card.findViewById<TextView>(R.id.tvLibraryVideoDescription).text = video.description
            card.findViewById<View>(R.id.btnOpenVideo).setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(video.url)))
            }
            videosContainer.addView(card)
        }
    }

    private fun selectCategory(category: String) {
        selectedCategory = category
        updateCategoryButtons()
        renderTopics()
    }

    private fun updateCategoryButtons() {
        updateCategoryButton(btnCategoryAll, selectedCategory == "All")
        updateCategoryButton(btnCategorySafety, selectedCategory == "Safety")
        updateCategoryButton(btnCategoryEducation, selectedCategory == "Education")
        updateCategoryButton(btnCategoryJustice, selectedCategory == "Justice")
    }

    private fun updateCategoryButton(button: AppCompatButton, selected: Boolean) {
        button.setBackgroundResource(
            if (selected) R.drawable.bg_library_chip_active else R.drawable.bg_library_chip_inactive
        )
        button.setTextColor(
            getColor(if (selected) R.color.white else R.color.home_text_primary)
        )
    }
}
