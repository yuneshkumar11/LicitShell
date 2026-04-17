package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val root = findViewById<View>(R.id.profileRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        findViewById<View>(R.id.btnProfileSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<TextView>(R.id.tvProfileName).text =
            user.displayName?.takeIf { it.isNotBlank() } ?: getString(R.string.unknown_user)
        findViewById<TextView>(R.id.tvProfileEmail).text =
            user.email ?: getString(R.string.not_available)

        // Real-time listener for user data
        database.child(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // This helps debug if the user node wasn't created
                    Toast.makeText(this@ProfileActivity, "User data not found in DB", Toast.LENGTH_SHORT).show()
                    return
                }
                
                val firebaseUser = snapshot.getValue(User::class.java)
                val joinedCommunities = firebaseUser?.joinedSocieties ?: emptyList()
                val qualifiedQuizzes = firebaseUser?.qualifiedQuizzes ?: emptyList()

                findViewById<TextView>(R.id.tvProfileCommunities).text =
                    if (joinedCommunities.isEmpty()) {
                        getString(R.string.profile_no_communities)
                    } else {
                        joinedCommunities.sorted().joinToString("\n")
                    }
                findViewById<TextView>(R.id.tvProfileQualifiedQuizzes).text =
                    if (qualifiedQuizzes.isEmpty()) {
                        getString(R.string.profile_no_quizzes)
                    } else {
                        qualifiedQuizzes.sorted().joinToString("\n")
                    }
                
                // Update Name and Status if they exist in DB
                firebaseUser?.name?.let { findViewById<TextView>(R.id.tvProfileName).text = it }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "DB Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        findViewById<View>(R.id.btnLogout).setOnClickListener {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                AiConversationStore.clearMessagesForUser(this, userId)
            }
            auth.signOut()
            Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}
