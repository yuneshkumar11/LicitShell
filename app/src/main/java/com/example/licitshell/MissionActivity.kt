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

class MissionActivity : AppCompatActivity() {

    private lateinit var missionsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mission)

        val root = findViewById<View>(R.id.missionRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        missionsContainer = findViewById(R.id.missionsStoryContainer)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        renderMissions()
    }

    private fun renderMissions() {
        missionsContainer.removeAllViews()
        MissionData.getMissions().forEach { mission ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_mission_story, missionsContainer, false)
            card.findViewById<TextView>(R.id.tvMissionTheme).text = mission.theme
            card.findViewById<TextView>(R.id.tvMissionTitle).text = mission.title
            card.findViewById<TextView>(R.id.tvMissionSubtitle).text = mission.subtitle
            card.findViewById<View>(R.id.btnOpenMission).setOnClickListener {
                startActivity(
                    Intent(this, MissionStoryActivity::class.java)
                        .putExtra("mission_key", mission.key)
                )
            }
            missionsContainer.addView(card)
        }
    }
}
