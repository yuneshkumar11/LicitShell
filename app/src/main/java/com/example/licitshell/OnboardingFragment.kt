package com.example.licitshell

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class OnboardingFragment : Fragment() {

    private var hasAnimated = false

    companion object {
        fun newInstance(position: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val bundle = Bundle()
            bundle.putInt("pos", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iconCard = view.findViewById<FrameLayout>(R.id.iconCard)
        val icon = view.findViewById<ImageView>(R.id.icon)
        val title = view.findViewById<TextView>(R.id.title)
        val desc = view.findViewById<TextView>(R.id.desc)

        val position = arguments?.getInt("pos") ?: 0

        when (position) {
            0 -> {
                icon.setImageResource(R.drawable.ic_game)
                title.text = getString(R.string.learn_rights_title)
                desc.text = getString(R.string.learn_rights_desc)
            }
            1 -> {
                icon.setImageResource(R.drawable.ic_shield)
                title.text = getString(R.string.stay_safe_title)
                desc.text = getString(R.string.stay_safe_desc)
            }
            2 -> {
                icon.setImageResource(R.drawable.ic_people)
                title.text = getString(R.string.connect_title)
                desc.text = getString(R.string.connect_desc)
            }
        }

        showFinalState(iconCard, title, desc)
    }

    fun playEntranceAnimation() {
        val root = view ?: return
        val iconCard = root.findViewById<FrameLayout>(R.id.iconCard)
        val title = root.findViewById<TextView>(R.id.title)
        val desc = root.findViewById<TextView>(R.id.desc)

        if (hasAnimated) {
            showFinalState(iconCard, title, desc)
            return
        }

        hasAnimated = true
        iconCard.alpha = 0f
        iconCard.scaleX = 0.82f
        iconCard.scaleY = 0.82f
        iconCard.translationY = 36f

        title.alpha = 0f
        title.translationY = 30f

        desc.alpha = 0f
        desc.translationY = 28f

        val iconFade = ObjectAnimator.ofFloat(iconCard, View.ALPHA, 0f, 1f)
        val iconScaleX = ObjectAnimator.ofFloat(iconCard, View.SCALE_X, 0.82f, 1f)
        val iconScaleY = ObjectAnimator.ofFloat(iconCard, View.SCALE_Y, 0.82f, 1f)
        val iconSlide = ObjectAnimator.ofFloat(iconCard, View.TRANSLATION_Y, 36f, 0f)

        AnimatorSet().apply {
            duration = 420L
            interpolator = OvershootInterpolator(0.85f)
            playTogether(iconFade, iconScaleX, iconScaleY, iconSlide)
            start()
        }

        title.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(120L)
            .setDuration(280L)
            .start()

        desc.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(180L)
            .setDuration(280L)
            .start()
    }

    fun resetForSelection() {
        hasAnimated = false
    }

    private fun showFinalState(iconCard: View, title: View, desc: View) {
        iconCard.alpha = 1f
        iconCard.scaleX = 1f
        iconCard.scaleY = 1f
        iconCard.translationY = 0f
        title.alpha = 1f
        title.translationY = 0f
        desc.alpha = 1f
        desc.translationY = 0f
    }
}
