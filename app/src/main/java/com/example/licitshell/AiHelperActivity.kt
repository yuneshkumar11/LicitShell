package com.example.licitshell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class AiHelperActivity : AppCompatActivity() {

    private lateinit var messagesContainer: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var inputMessage: EditText
    private lateinit var sendButton: AppCompatButton
    private lateinit var emptyStateView: TextView
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_helper)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            finish()
            return
        }
        userId = currentUser.uid

        val root = findViewById<View>(R.id.aiHelperRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        messagesContainer = findViewById(R.id.messagesContainer)
        scrollView = findViewById(R.id.messagesScrollView)
        inputMessage = findViewById(R.id.etAiMessage)
        sendButton = findViewById(R.id.btnSendAiMessage)
        emptyStateView = findViewById(R.id.tvAiEmptyState)

        findViewById<View>(R.id.btnAiBack).setOnClickListener { finish() }
        sendButton.setOnClickListener { sendCurrentMessage() }

        bindQuickPrompt(R.id.promptRoadSafety, "Explain road safety rules for students")
        bindQuickPrompt(R.id.promptMarriageLaw, "Explain marriage law basics in India")
        bindQuickPrompt(R.id.promptRights, "Tell me my basic constitutional rights")
        bindQuickPrompt(R.id.promptAiLearning, "How can AI help me learn faster?")

        loadConversation()
    }

    private fun bindQuickPrompt(viewId: Int, prompt: String) {
        findViewById<View>(viewId).setOnClickListener {
            inputMessage.setText(prompt)
            sendCurrentMessage()
        }
    }

    private fun sendCurrentMessage() {
        val question = inputMessage.text.toString().trim()
        if (question.isEmpty()) {
            inputMessage.error = getString(R.string.message_empty)
            return
        }

        appendMessage(
            AiConversationMessage(
                sender = getString(R.string.you_label),
                message = question,
                isUser = true
            ),
            saveToStore = true
        )
        inputMessage.text?.clear()
        setSendingState(true)

        Thread {
            val result = GeminiService.generateReply(question)
            runOnUiThread {
                setSendingState(false)
                result
                    .onSuccess { reply ->
                        appendMessage(
                            AiConversationMessage(
                                sender = getString(R.string.ai_helper_bot_name),
                                message = reply,
                                isUser = false
                            ),
                            saveToStore = true
                        )
                    }
                    .onFailure {
                        Toast.makeText(
                            this,
                            getString(R.string.ai_helper_error_notice),
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }.start()
    }

    private fun setSendingState(isSending: Boolean) {
        sendButton.isEnabled = !isSending
        inputMessage.isEnabled = !isSending
        sendButton.text = getString(if (isSending) R.string.ai_helper_thinking else R.string.send)
    }

    private fun loadConversation() {
        val messages = AiConversationStore.getMessages(this, userId)
        messages.forEach { appendMessage(it, saveToStore = false) }
        updateEmptyState()
    }

    private fun appendMessage(message: AiConversationMessage, saveToStore: Boolean) {
        if (saveToStore) {
            AiConversationStore.addMessage(this, userId, message)
        }

        val itemView = LayoutInflater.from(this).inflate(
            R.layout.item_ai_message,
            messagesContainer,
            false
        )

        val senderView = itemView.findViewById<TextView>(R.id.tvMessageSender)
        val bodyView = itemView.findViewById<TextView>(R.id.tvMessageBody)
        val bubbleView = itemView.findViewById<View>(R.id.messageBubble)

        senderView.text = message.sender
        bodyView.text = message.message
        bubbleView.setBackgroundResource(
            if (message.isUser) R.drawable.bg_ai_message_user else R.drawable.bg_ai_message_bot
        )
        senderView.setTextColor(
            ContextCompat.getColor(
                this,
                if (message.isUser) R.color.white else R.color.home_text_secondary
            )
        )
        bodyView.setTextColor(
            ContextCompat.getColor(
                this,
                if (message.isUser) R.color.white else R.color.home_text_primary
            )
        )

        val params = bubbleView.layoutParams as LinearLayout.LayoutParams
        params.gravity = if (message.isUser) android.view.Gravity.END else android.view.Gravity.START
        bubbleView.layoutParams = params

        messagesContainer.addView(itemView)
        updateEmptyState()
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    private fun updateEmptyState() {
        emptyStateView.visibility = if (messagesContainer.childCount == 0) View.VISIBLE else View.GONE
    }
}
