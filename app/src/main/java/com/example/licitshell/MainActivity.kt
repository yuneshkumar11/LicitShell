package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val root = findViewById<View>(R.id.mainRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        val user = auth.currentUser
        findViewById<TextView>(R.id.tvHeroName).text =
            user?.displayName?.takeIf { it.isNotBlank() } ?: getString(R.string.unknown_user)

        findViewById<View>(R.id.homeProfileButton).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        findViewById<View>(R.id.notificationButton).setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<View>(R.id.moduleLibrary).setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }
        findViewById<View>(R.id.moduleCommunity).setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
        }
        findViewById<View>(R.id.moduleEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyActivity::class.java))
        }
        findViewById<View>(R.id.moduleGovernment).setOnClickListener {
            startActivity(Intent(this, GovernmentActivity::class.java))
        }
        findViewById<View>(R.id.moduleBookBank).setOnClickListener {
            startActivity(Intent(this, BookBankActivity::class.java))
        }
        findViewById<View>(R.id.moduleAi).setOnClickListener {
            startActivity(Intent(this, AiHelperActivity::class.java))
        }
        findViewById<View>(R.id.moduleQuiz).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
        findViewById<View>(R.id.moduleMissions).setOnClickListener {
            startActivity(Intent(this, MissionActivity::class.java))
        }
    }
}
