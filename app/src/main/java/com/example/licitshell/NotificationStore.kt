package com.example.licitshell

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class AppNotification(
    val title: String,
    val message: String,
    val timestamp: Long
)

object NotificationStore {

    private const val PREFS_NAME = "licit_shell_notifications"
    private const val KEY_NOTIFICATIONS = "notifications"

    fun addNotification(context: Context, title: String, message: String) {
        val current = getNotifications(context).toMutableList()
        current.add(
            0,
            AppNotification(
                title = title,
                message = message,
                timestamp = System.currentTimeMillis()
            )
        )
        saveNotifications(context, current.take(50))
    }

    fun getNotifications(context: Context): List<AppNotification> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_NOTIFICATIONS, "[]").orEmpty()
        val array = JSONArray(raw)
        val items = mutableListOf<AppNotification>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            items.add(
                AppNotification(
                    title = obj.optString("title"),
                    message = obj.optString("message"),
                    timestamp = obj.optLong("timestamp")
                )
            )
        }
        return items
    }

    private fun saveNotifications(context: Context, notifications: List<AppNotification>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val array = JSONArray()
        notifications.forEach { notification ->
            array.put(
                JSONObject().apply {
                    put("title", notification.title)
                    put("message", notification.message)
                    put("timestamp", notification.timestamp)
                }
            )
        }
        prefs.edit().putString(KEY_NOTIFICATIONS, array.toString()).apply()
    }
}
