package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val root = findViewById<View>(R.id.loginScreenRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        findViewById<TextView>(R.id.tvRegister).text =
            Html.fromHtml(getString(R.string.register_prompt), Html.FROM_HTML_MODE_LEGACY)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            loginUser()
        }
        findViewById<View>(R.id.btnGoogle).setOnClickListener {
            Toast.makeText(this, getString(R.string.google_coming_soon), Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        animateLoginUi()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            openMain()
        }
    }

    private fun loginUser() {
        val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(
                        this,
                        task.exception?.localizedMessage ?: getString(R.string.auth_failed),
                        Toast.LENGTH_LONG
                    ).show()
                    return@addOnCompleteListener
                }

                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                openMain()
            }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun animateLoginUi() {
        val logoCard = findViewById<View>(R.id.loginLogoCard)
        val loginCard = findViewById<View>(R.id.loginCard)

        logoCard.alpha = 0f
        logoCard.scaleX = 0.8f
        logoCard.scaleY = 0.8f
        logoCard.translationY = 24f

        loginCard.alpha = 0f
        loginCard.translationY = 56f
        loginCard.scaleX = 0.97f
        loginCard.scaleY = 0.97f

        logoCard.animate()
            .alpha(1f)
            .translationY(0f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(320L)
            .start()

        loginCard.animate()
            .alpha(1f)
            .translationY(0f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(80L)
            .setDuration(420L)
            .start()
    }
}
