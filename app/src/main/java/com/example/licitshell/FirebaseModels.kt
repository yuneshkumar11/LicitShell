package com.example.licitshell

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val status: String? = null,
    @get:PropertyName("isWillingToChat")
    @set:PropertyName("isWillingToChat")
    var isWillingToChat: Boolean = false,
    val joinedSocieties: List<String>? = null,
    val qualifiedQuizzes: List<String>? = null
)

@IgnoreExtraProperties
data class Society(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val membersCount: Int = 0,
    val postsCount: Int = 0,
    val createdBy: String? = null
)

@IgnoreExtraProperties
data class SocietyPost(
    val id: String? = null,
    val authorName: String? = null,
    val content: String? = null,
    val timestamp: Long = 0
)

@IgnoreExtraProperties
data class ChatMessage(
    val senderId: String? = null,
    val text: String? = null,
    val timestamp: Long = 0
)
