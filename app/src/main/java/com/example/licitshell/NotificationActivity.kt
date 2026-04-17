package com.example.licitshell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationContainer: LinearLayout
    private lateinit var tvEmptyNotifications: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)

        val root = findViewById<View>(R.id.notificationRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        notificationContainer = findViewById(R.id.notificationContainer)
        tvEmptyNotifications = findViewById(R.id.tvEmptyNotifications)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        renderNotifications()
    }

    private fun renderNotifications() {
        val notifications = NotificationStore.getNotifications(this)
        notificationContainer.removeAllViews()
        tvEmptyNotifications.visibility = if (notifications.isEmpty()) View.VISIBLE else View.GONE

        val formatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        notifications.forEach { notification ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_notification, notificationContainer, false)
            itemView.findViewById<TextView>(R.id.tvNotificationTitle).text = notification.title
            itemView.findViewById<TextView>(R.id.tvNotificationMessage).text = notification.message
            itemView.findViewById<TextView>(R.id.tvNotificationTime).text =
                formatter.format(Date(notification.timestamp))
            notificationContainer.addView(itemView)
        }
    }
}
