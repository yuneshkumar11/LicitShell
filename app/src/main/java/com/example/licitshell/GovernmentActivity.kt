package com.example.licitshell

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GovernmentActivity : AppCompatActivity() {

    private lateinit var categoryButtons: List<Button>
    private lateinit var sections: Map<String, View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_government)

        val root = findViewById<View>(R.id.governmentRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        categoryButtons = listOf(
            findViewById(R.id.btnCategoryEducation),
            findViewById(R.id.btnCategoryFinance),
            findViewById(R.id.btnCategoryLegal),
            findViewById(R.id.btnCategoryOther)
        )

        sections = mapOf(
            "education" to findViewById(R.id.sectionEducation),
            "finance" to findViewById(R.id.sectionFinance),
            "legal" to findViewById(R.id.sectionLegal),
            "other" to findViewById(R.id.sectionOther)
        )

        findViewById<Button>(R.id.btnCategoryEducation).setOnClickListener { showCategory("education") }
        findViewById<Button>(R.id.btnCategoryFinance).setOnClickListener { showCategory("finance") }
        findViewById<Button>(R.id.btnCategoryLegal).setOnClickListener { showCategory("legal") }
        findViewById<Button>(R.id.btnCategoryOther).setOnClickListener { showCategory("other") }

        bindLink(
            R.id.govCardEducation,
            getString(R.string.ministry_education),
            getString(R.string.ministry_education_desc),
            "https://www.education.gov.in/en"
        )
        bindLink(
            R.id.govCardDiksha,
            getString(R.string.diksha),
            getString(R.string.diksha_desc),
            "https://www.diksha.gov.in/"
        )
        bindLink(
            R.id.govCardSwayam,
            getString(R.string.swayam),
            getString(R.string.swayam_desc),
            "https://swayam.gov.in/"
        )
        bindLink(
            R.id.govCardScholarship,
            getString(R.string.national_scholarship),
            getString(R.string.national_scholarship_desc),
            "https://scholarships.gov.in/"
        )
        bindLink(
            R.id.govCardIncomeTax,
            getString(R.string.income_tax),
            getString(R.string.income_tax_desc),
            "https://www.incometax.gov.in/"
        )
        bindLink(
            R.id.govCardPan,
            getString(R.string.pan_services),
            getString(R.string.pan_services_desc),
            "https://www.incometaxindia.gov.in/tax-services/apply-for-pan"
        )
        bindLink(
            R.id.govCardGst,
            getString(R.string.gst),
            getString(R.string.gst_desc),
            "https://www.gst.gov.in/"
        )
        bindLink(
            R.id.govCardPmKisan,
            getString(R.string.pm_kisan),
            getString(R.string.pm_kisan_desc),
            "https://pmkisan.gov.in/"
        )
        bindLink(
            R.id.govCardLawMin,
            getString(R.string.ministry_law_justice),
            getString(R.string.ministry_law_justice_desc),
            "https://lawmin.gov.in/"
        )
        bindLink(
            R.id.govCardDoj,
            getString(R.string.department_justice),
            getString(R.string.department_justice_desc),
            "https://doj.gov.in/"
        )
        bindLink(
            R.id.govCardNalsa,
            getString(R.string.nalsa),
            getString(R.string.nalsa_desc),
            "https://nalsa.gov.in/"
        )
        bindLink(
            R.id.govCardIndiaCode,
            getString(R.string.india_code),
            getString(R.string.india_code_desc),
            "https://www.indiacode.nic.in/"
        )
        bindLink(
            R.id.govCardSupremeCourt,
            getString(R.string.supreme_court),
            getString(R.string.supreme_court_desc),
            "https://www.sci.gov.in/"
        )
        bindLink(
            R.id.govCardEcourts,
            getString(R.string.ecourts),
            getString(R.string.ecourts_desc),
            "https://services.ecourts.gov.in/"
        )
        bindLink(
            R.id.govCardDigilocker,
            getString(R.string.digilocker),
            getString(R.string.digilocker_desc),
            "https://www.digilocker.gov.in/"
        )
        bindLink(
            R.id.govCardUidai,
            getString(R.string.uidai),
            getString(R.string.uidai_desc),
            "https://uidai.gov.in/"
        )
        bindLink(
            R.id.govCardPassport,
            getString(R.string.passport_seva),
            getString(R.string.passport_seva_desc),
            "https://www.passportindia.gov.in/"
        )
        bindLink(
            R.id.govCardUmang,
            getString(R.string.umang),
            getString(R.string.umang_desc),
            "https://web.umang.gov.in/"
        )

        showCategory("education")
    }

    private fun bindLink(cardId: Int, title: String, desc: String, url: String) {
        val card = findViewById<View>(cardId)
        card.findViewById<TextView>(R.id.tvGovTitle).text = title
        card.findViewById<TextView>(R.id.tvGovDesc).text = desc
        card.findViewById<View>(R.id.btnOpenGov).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    private fun showCategory(category: String) {
        sections.forEach { (key, view) ->
            view.visibility = if (key == category) View.VISIBLE else View.GONE
        }

        categoryButtons.forEach { button ->
            val isSelected = when (button.id) {
                R.id.btnCategoryEducation -> category == "education"
                R.id.btnCategoryFinance -> category == "finance"
                R.id.btnCategoryLegal -> category == "legal"
                else -> category == "other"
            }

            button.setBackgroundResource(
                if (isSelected) R.drawable.bg_government_chip_active else R.drawable.bg_government_chip_inactive
            )
            button.setTextColor(
                if (isSelected) getColor(R.color.splash_logo_blue) else getColor(R.color.white)
            )
        }
    }
}
