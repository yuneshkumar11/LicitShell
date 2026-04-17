package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var skipText: TextView
    private lateinit var indicators: List<android.view.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val root = findViewById<View>(R.id.onboardingRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        skipText = findViewById(R.id.tvSkip)
        indicators = listOf(
            findViewById(R.id.indicatorOne),
            findViewById(R.id.indicatorTwo),
            findViewById(R.id.indicatorThree)
        )

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        updateUiForPage(0)
        animatePrimaryControls()
        viewPager.post { animateCurrentPage(0) }

        skipText.setOnClickListener {
            openLogin()
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < 2) {
                viewPager.currentItem += 1
            } else {
                openLogin()
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateUiForPage(position)
                animateCurrentPage(position)
            }
        })
    }

    private fun updateUiForPage(position: Int) {
        indicators.forEachIndexed { index, view ->
            view.setBackgroundResource(
                if (index == position) R.drawable.indicator_active else R.drawable.indicator_inactive
            )
            view.animate()
                .scaleX(if (index == position) 1.05f else 1f)
                .setDuration(180L)
                .start()
        }

        btnNext.text = if (position == indicators.lastIndex) {
            getString(R.string.enter_licit_shell)
        } else {
            getString(R.string.next)
        }

        animatePrimaryControls()
    }

    private fun animatePrimaryControls() {
        btnNext.animate()
            .alpha(0f)
            .translationY(12f)
            .setDuration(0L)
            .withEndAction {
                btnNext.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(220L)
                    .start()
            }
            .start()
    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun animateCurrentPage(position: Int) {
        val fragment = supportFragmentManager.findFragmentByTag("f$position") as? OnboardingFragment ?: return
        fragment.resetForSelection()
        fragment.playEntranceAnimation()
    }
}
