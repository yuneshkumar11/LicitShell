package com.example.licitshell

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatRoomActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("chats")
    private var chatRoomId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_room)

        val root = findViewById<View>(R.id.chatRoomRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        val myUid = auth.currentUser?.uid ?: return
        val otherUid = intent.getStringExtra("chat_uid") ?: return
        val chatName = intent.getStringExtra("chat_name") ?: getString(R.string.unknown_user)
        
        // Generate a unique ID for the chat between these two users
        chatRoomId = if (myUid < otherUid) "${myUid}_${otherUid}" else "${otherUid}_${myUid}"

        findViewById<TextView>(R.id.tvChatRoomTitle).text = getString(R.string.chat_with, chatName)
        findViewById<View>(R.id.btnChatBack).setOnClickListener { finish() }

        val messagesContainer = findViewById<LinearLayout>(R.id.messagesContainer)

        listenForMessages(messagesContainer)

        findViewById<View>(R.id.btnSend).setOnClickListener {
            val etMessage = findViewById<EditText>(R.id.etMessage)
            val messageText = etMessage.text.toString().trim()
            if (messageText.isEmpty()) {
                Toast.makeText(this, getString(R.string.message_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val message = ChatMessage(
                senderId = myUid,
                text = messageText,
                timestamp = System.currentTimeMillis()
            )
            
            chatRoomId?.let { roomId ->
                db.child(roomId).push().setValue(message)
            }
            etMessage.text.clear()
        }
    }

    private fun listenForMessages(container: LinearLayout) {
        val roomId = chatRoomId ?: return
        val myUid = auth.currentUser?.uid ?: return

        db.child(roomId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                container.removeAllViews()
                for (child in snapshot.children) {
                    val msg = child.getValue(ChatMessage::class.java)
                    if (msg != null) {
                        val isMe = msg.senderId == myUid
                        val senderName = if (isMe) getString(R.string.you_label) else intent.getStringExtra("chat_name") ?: "Other"
                        addMessage(container, senderName, msg.text ?: "", isMe)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatRoomActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addMessage(container: LinearLayout, sender: String, message: String, isUser: Boolean) {
        val item = LayoutInflater.from(this).inflate(R.layout.item_chat_message, container, false)
        val row = item.findViewById<LinearLayout>(R.id.messageRow)
        val tvSender = item.findViewById<TextView>(R.id.tvSender)
        val tvBody = item.findViewById<TextView>(R.id.tvMessageBody)
        tvSender.text = sender
        tvBody.text = message
        tvBody.setBackgroundResource(if (isUser) R.drawable.bg_chat_user else R.drawable.bg_chat_other)
        row.gravity = if (isUser) Gravity.END else Gravity.START
        container.addView(item)
    }
}
