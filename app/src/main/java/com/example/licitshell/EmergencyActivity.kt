package com.example.licitshell

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmergencyActivity : AppCompatActivity() {

    private var pendingNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emergency)

        val root = findViewById<View>(R.id.emergencyRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        bindContact(R.id.card112, getString(R.string.national_emergency), getString(R.string.national_emergency_desc), "112")
        bindContact(R.id.card100, getString(R.string.police), getString(R.string.police_desc), "100")
        bindContact(R.id.card108, getString(R.string.ambulance), getString(R.string.ambulance_desc), "108")
        bindContact(R.id.card101, getString(R.string.fire_service), getString(R.string.fire_service_desc), "101")
        bindContact(R.id.card1091, getString(R.string.women_helpline), getString(R.string.women_helpline_desc), "1091")
        bindContact(R.id.card1098, getString(R.string.child_helpline), getString(R.string.child_helpline_desc), "1098")
    }

    private fun bindContact(cardId: Int, title: String, desc: String, number: String) {
        val card = findViewById<View>(cardId)
        card.findViewById<TextView>(R.id.tvContactTitle).text = title
        card.findViewById<TextView>(R.id.tvContactDesc).text = desc
        card.findViewById<TextView>(R.id.tvContactNumber).text = number
        card.findViewById<View>(R.id.btnCall).setOnClickListener {
            startCall(number)
        }
    }

    private fun startCall(number: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
        } else {
            pendingNumber = number
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 9001)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 9001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pendingNumber?.let { startCall(it) }
            pendingNumber = null
        } else if (requestCode == 9001) {
            Toast.makeText(this, getString(R.string.call_permission_denied), Toast.LENGTH_LONG).show()
        }
    }
}
