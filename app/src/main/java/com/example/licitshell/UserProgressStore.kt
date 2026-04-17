package com.example.licitshell

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object UserProgressStore {

    private const val PREFS_NAME = "licit_shell_progress"
    private const val KEY_JOINED_SOCIETIES = "joined_societies"
    private const val KEY_PASSED_QUIZZES = "passed_quizzes"

    private val database = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")
    private val auth = FirebaseAuth.getInstance()

    fun getJoinedSocieties(context: Context): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_JOINED_SOCIETIES, emptySet())?.toMutableSet() ?: mutableSetOf()
    }

    private fun saveJoinedSocieties(context: Context, societies: Set<String>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putStringSet(KEY_JOINED_SOCIETIES, societies).apply()
        
        // Sync to Firebase
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child(userId).child("joinedSocieties").setValue(societies.toList())
        }
    }

    fun addJoinedSociety(context: Context, title: String) {
        val updated = getJoinedSocieties(context)
        if (updated.add(title)) {
            saveJoinedSocieties(context, updated)
        }
    }

    fun removeJoinedSociety(context: Context, title: String) {
        val updated = getJoinedSocieties(context)
        if (updated.remove(title)) {
            saveJoinedSocieties(context, updated)
        }
    }

    fun getPassedQuizzes(context: Context): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_PASSED_QUIZZES, emptySet())?.toMutableSet() ?: mutableSetOf()
    }

    fun addPassedQuiz(context: Context, quizTitle: String) {
        val updated = getPassedQuizzes(context)
        if (updated.add(quizTitle)) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putStringSet(KEY_PASSED_QUIZZES, updated).apply()
            
            // Sync to Firebase
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child(userId).child("qualifiedQuizzes").setValue(updated.toList())
        }
        }
    }
}
