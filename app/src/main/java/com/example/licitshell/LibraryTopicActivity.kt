package com.example.licitshell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LibraryTopicActivity : AppCompatActivity() {

    private data class TopicSection(
        val title: String,
        val body: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_library_topic)

        val root = findViewById<View>(R.id.libraryTopicRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        val topicId = intent.getIntExtra("topic_id", -1)
        val topic = LibraryData.getTopicById(topicId)

        val titleView = findViewById<TextView>(R.id.tvLibraryDetailTitle)
        val subtitleView = findViewById<TextView>(R.id.tvLibraryDetailSubtitle)
        val moduleLabel = findViewById<TextView>(R.id.tvLibraryModuleLabel)
        val highlightsContainer = findViewById<LinearLayout>(R.id.highlightsContainer)
        val sectionsContainer = findViewById<LinearLayout>(R.id.sectionsContainer)

        if (topic != null) {
            titleView.text = topic.title
            subtitleView.text = topic.subtitle
            moduleLabel.text = getString(R.string.library_module_with_number, topic.id)
            val sections = parseSections(topic.content)
            renderHighlights(highlightsContainer, extractHighlights(sections))
            renderSections(sectionsContainer, sections)
        } else {
            titleView.text = getString(R.string.library_topic_not_found)
            subtitleView.text = getString(R.string.library_topic_not_found_desc)
            moduleLabel.text = getString(R.string.library_module)
            highlightsContainer.removeAllViews()
            sectionsContainer.removeAllViews()
        }
    }

    private fun parseSections(content: String): List<TopicSection> {
        return content
            .split("\n\n")
            .mapNotNull { block ->
                val lines = block.lines().map { it.trim() }.filter { it.isNotEmpty() }
                if (lines.isEmpty()) {
                    null
                } else {
                    TopicSection(
                        title = lines.first(),
                        body = lines.drop(1).joinToString("\n")
                    )
                }
            }
    }

    private fun extractHighlights(sections: List<TopicSection>): List<String> {
        val highlights = mutableListOf<String>()

        sections.forEach { section ->
            section.body.lines()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .forEach { line ->
                    val isImportantLine =
                        line.startsWith("Article") ||
                            line.startsWith("Part") ||
                            line.startsWith("Schedule") ||
                            line.contains("Amendment") ||
                            line.contains("Fundamental Right") ||
                            line.contains("Fundamental Duty")

                    if (isImportantLine && highlights.size < 6) {
                        highlights += line
                    }
                }
        }

        if (highlights.isEmpty()) {
            sections.take(4).forEach { section ->
                section.body.lines()
                    .map { it.trim() }
                    .firstOrNull { it.isNotEmpty() }
                    ?.let { highlights += it }
            }
        }

        return highlights.distinct().take(6)
    }

    private fun renderHighlights(container: LinearLayout, highlights: List<String>) {
        container.removeAllViews()
        val items = if (highlights.isEmpty()) listOf(getString(R.string.no_highlights_available)) else highlights

        items.forEachIndexed { index, text ->
            val view = LayoutInflater.from(this).inflate(R.layout.item_library_highlight, container, false)
            view.findViewById<TextView>(R.id.tvHighlightIndex).text = (index + 1).toString()
            view.findViewById<TextView>(R.id.tvHighlightText).text = text
            container.addView(view)
        }
    }

    private fun renderSections(container: LinearLayout, sections: List<TopicSection>) {
        container.removeAllViews()
        sections.forEach { section ->
            val view = LayoutInflater.from(this).inflate(R.layout.item_library_section, container, false)
            view.findViewById<TextView>(R.id.tvSectionTitle).text = section.title
            view.findViewById<TextView>(R.id.tvSectionBody).text = section.body
            container.addView(view)
        }
    }
}
