package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val root = findViewById<View>(R.id.registerScreenRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        findViewById<TextView>(R.id.tvLogin).text =
            Html.fromHtml(getString(R.string.login_prompt), Html.FROM_HTML_MODE_LEGACY)

        findViewById<View>(R.id.btnRegister).setOnClickListener { registerUser() }
        findViewById<View>(R.id.btnRegisterGoogle).setOnClickListener {
            Toast.makeText(this, getString(R.string.google_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.tvLogin).setOnClickListener { finish() }

        animateRegisterUi()
    }

    private fun registerUser() {
        val name = findViewById<EditText>(R.id.etName).text.toString().trim()
        val email = findViewById<EditText>(R.id.etRegisterEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.etRegisterPassword).text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(
                        this,
                        task.exception?.localizedMessage ?: getString(R.string.auth_failed),
                        Toast.LENGTH_LONG
                    ).show()
                    return@addOnCompleteListener
                }

                val updates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                val currentUser = auth.currentUser
                currentUser?.updateProfile(updates)?.addOnCompleteListener {
                    // Initialize user in Realtime Database
                    val userId = currentUser.uid
                    val newUser = User(
                        name = name,
                        email = email,
                        status = "Learning about Law",
                        isWillingToChat = false,
                        joinedSocieties = emptyList(),
                        qualifiedQuizzes = emptyList()
                    )
                    
                    // Note: Use .setValue().addOnFailureListener to debug
                    database.child(userId).setValue(newUser)
                        .addOnSuccessListener {
                            Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                            openMain()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Firebase DB Error: ${e.message}", Toast.LENGTH_LONG).show()
                            openMain()
                        }
                } ?: openMain()
            }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    private fun animateRegisterUi() {
        val root = findViewById<View>(R.id.registerRoot)
        root.alpha = 0f
        root.translationY = 40f
        root.scaleX = 0.97f
        root.scaleY = 0.97f
        root.animate().alpha(1f).translationY(0f).scaleX(1f).scaleY(1f).setDuration(350L).start()
    }
}
