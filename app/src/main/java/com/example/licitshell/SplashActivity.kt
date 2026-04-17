package com.example.licitshell

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        startSplashAnimation()

        handler.postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, 2800)
    }

    private fun startSplashAnimation() {
        val contentGroup = findViewById<View>(R.id.contentGroup)
        contentGroup.translationY = 42f
        contentGroup.scaleX = 0.94f
        contentGroup.scaleY = 0.94f

        val fadeIn = ObjectAnimator.ofFloat(contentGroup, View.ALPHA, 0f, 1f)
        val slideUp = ObjectAnimator.ofFloat(contentGroup, View.TRANSLATION_Y, 42f, 0f)
        val scaleX = ObjectAnimator.ofFloat(contentGroup, View.SCALE_X, 0.94f, 1f)
        val scaleY = ObjectAnimator.ofFloat(contentGroup, View.SCALE_Y, 0.94f, 1f)

        AnimatorSet().apply {
            duration = 900L
            interpolator = OvershootInterpolator(0.8f)
            playTogether(fadeIn, slideUp, scaleX, scaleY)
            start()
        }

        animateDots(
            findViewById(R.id.dotOne),
            findViewById(R.id.dotTwo),
            findViewById(R.id.dotThree)
        )
    }

    private fun animateDots(vararg dots: View) {
        dots.forEachIndexed { index, dot ->
            val alphaAnimator = ObjectAnimator.ofFloat(dot, View.ALPHA, 0.35f, 1f, 0.35f).apply {
                duration = 900L
                startDelay = index * 160L
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
            }

            val scaleXAnimator = ObjectAnimator.ofFloat(dot, View.SCALE_X, 1f, 1.28f, 1f).apply {
                duration = 900L
                startDelay = index * 160L
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
            }

            val scaleYAnimator = ObjectAnimator.ofFloat(dot, View.SCALE_Y, 1f, 1.28f, 1f).apply {
                duration = 900L
                startDelay = index * 160L
                repeatCount = ObjectAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
            }

            AnimatorSet().apply {
                playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
                start()
            }
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
