package com.example.licitshell

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class AiConversationMessage(
    val sender: String,
    val message: String,
    val isUser: Boolean
)

object AiConversationStore {

    fun getMessages(context: Context, userId: String): List<AiConversationMessage> {
        val db = AiConversationDbHelper(context).readableDatabase
        val messages = mutableListOf<AiConversationMessage>()

        db.query(
            AiConversationDbHelper.TABLE_MESSAGES,
            arrayOf(
                AiConversationDbHelper.COLUMN_SENDER,
                AiConversationDbHelper.COLUMN_MESSAGE,
                AiConversationDbHelper.COLUMN_IS_USER
            ),
            "${AiConversationDbHelper.COLUMN_USER_ID} = ?",
            arrayOf(userId),
            null,
            null,
            "${AiConversationDbHelper.COLUMN_CREATED_AT} ASC, ${AiConversationDbHelper.COLUMN_ID} ASC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                messages.add(
                    AiConversationMessage(
                        sender = cursor.getString(0),
                        message = cursor.getString(1),
                        isUser = cursor.getInt(2) == 1
                    )
                )
            }
        }

        db.close()
        return messages
    }

    fun addMessage(context: Context, userId: String, message: AiConversationMessage) {
        val db = AiConversationDbHelper(context).writableDatabase
        val values = ContentValues().apply {
            put(AiConversationDbHelper.COLUMN_USER_ID, userId)
            put(AiConversationDbHelper.COLUMN_SENDER, message.sender)
            put(AiConversationDbHelper.COLUMN_MESSAGE, message.message)
            put(AiConversationDbHelper.COLUMN_IS_USER, if (message.isUser) 1 else 0)
            put(AiConversationDbHelper.COLUMN_CREATED_AT, System.currentTimeMillis())
        }
        db.insert(AiConversationDbHelper.TABLE_MESSAGES, null, values)
        db.close()
    }

    fun clearMessagesForUser(context: Context, userId: String) {
        val db = AiConversationDbHelper(context).writableDatabase
        db.delete(
            AiConversationDbHelper.TABLE_MESSAGES,
            "${AiConversationDbHelper.COLUMN_USER_ID} = ?",
            arrayOf(userId)
        )
        db.close()
    }

    private class AiConversationDbHelper(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE $TABLE_MESSAGES (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USER_ID TEXT NOT NULL,
                    $COLUMN_SENDER TEXT NOT NULL,
                    $COLUMN_MESSAGE TEXT NOT NULL,
                    $COLUMN_IS_USER INTEGER NOT NULL,
                    $COLUMN_CREATED_AT INTEGER NOT NULL
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_MESSAGES")
            onCreate(db)
        }

        companion object {
            private const val DB_NAME = "ai_conversation.db"
            private const val DB_VERSION = 1

            const val TABLE_MESSAGES = "messages"
            const val COLUMN_ID = "id"
            const val COLUMN_USER_ID = "user_id"
            const val COLUMN_SENDER = "sender"
            const val COLUMN_MESSAGE = "message"
            const val COLUMN_IS_USER = "is_user"
            const val COLUMN_CREATED_AT = "created_at"
        }
    }
}
