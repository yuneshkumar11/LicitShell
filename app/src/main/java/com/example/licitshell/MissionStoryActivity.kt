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

class MissionStoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mission_story)

        val root = findViewById<View>(R.id.missionStoryRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        val mission = MissionData.getMissionByKey(intent.getStringExtra("mission_key").orEmpty())
        val titleView = findViewById<TextView>(R.id.tvMissionStoryTitle)
        val subtitleView = findViewById<TextView>(R.id.tvMissionStorySubtitle)
        val themeView = findViewById<TextView>(R.id.tvMissionStoryTheme)
        val pagesContainer = findViewById<LinearLayout>(R.id.storyPagesContainer)

        if (mission == null) {
            titleView.text = getString(R.string.mission_not_found)
            subtitleView.text = getString(R.string.mission_not_found_desc)
            themeView.text = getString(R.string.mission_theme_default)
            return
        }

        titleView.text = mission.title
        subtitleView.text = mission.subtitle
        themeView.text = mission.theme

        pagesContainer.removeAllViews()
        mission.pages.forEachIndexed { index, page ->
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_story_page, pagesContainer, false)
            itemView.findViewById<TextView>(R.id.tvStoryPageIndex).text =
                getString(R.string.story_page_number, index + 1)
            itemView.findViewById<TextView>(R.id.tvStoryPageTitle).text = page.title
            itemView.findViewById<TextView>(R.id.tvStoryPageBody).text = page.story
            itemView.findViewById<TextView>(R.id.tvStoryPageLesson).text = page.lesson
            pagesContainer.addView(itemView)
        }
    }
}
