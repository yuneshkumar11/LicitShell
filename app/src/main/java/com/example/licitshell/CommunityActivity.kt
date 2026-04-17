package com.example.licitshell

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CommunityActivity : AppCompatActivity() {

    private lateinit var societiesSection: View
    private lateinit var chatSection: View
    private lateinit var btnSocietiesTab: AppCompatButton
    private lateinit var btnChatTab: AppCompatButton
    private lateinit var societiesContainer: LinearLayout
    private lateinit var chatUsersContainer: LinearLayout
    private lateinit var tvPeopleReady: TextView
    private lateinit var tvChatHint: TextView
    private lateinit var etSearchSocieties: EditText

    private val societiesList = mutableListOf<Society>()
    private val chatUsersList = mutableListOf<User>()
    private val joinedSocietyTitles = mutableSetOf<String>()

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val societiesRef = db.getReference("societies")
    private val usersRef = db.getReference("users")

    private val societyRoomLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val exitedSocietyTitle = result.data?.getStringExtra("exited_society_title").orEmpty()
            if (exitedSocietyTitle.isNotEmpty()) {
                handleExitSociety(exitedSocietyTitle)
            }
        }

    private var currentSearchQuery: String = ""
    private var isChatTabSelected: Boolean = false
    private var isWillingToChatEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_community)

        val root = findViewById<View>(R.id.communityRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        societiesSection = findViewById(R.id.societiesSection)
        chatSection = findViewById(R.id.chatSection)
        btnSocietiesTab = findViewById(R.id.btnSocietiesTab)
        btnChatTab = findViewById(R.id.btnChatTab)
        societiesContainer = findViewById(R.id.societiesContainer)
        chatUsersContainer = findViewById(R.id.chatUsersContainer)
        tvPeopleReady = findViewById(R.id.tvPeopleReady)
        tvChatHint = findViewById(R.id.tvChatHint)
        etSearchSocieties = findViewById(R.id.etSearchSocieties)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnCreateSociety).setOnClickListener { showCreateSocietyDialog() }
        val swWillingToChat = findViewById<SwitchCompat>(R.id.switchWillingToChat)
        swWillingToChat.setOnClickListener {
            val checked = (it as SwitchCompat).isChecked
            updateMyChatAvailability(checked)
        }

        btnSocietiesTab.setOnClickListener { showSocietiesTab() }
        btnChatTab.setOnClickListener { showChatTab() }
        etSearchSocieties.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchQuery = s?.toString().orEmpty()
                renderCurrentTab()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        listenToSocieties()
        listenToMyData()
        listenToChatUsers()
        showSocietiesTab()
    }

    private fun listenToSocieties() {
        societiesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                societiesList.clear()
                for (child in snapshot.children) {
                    val society = child.getValue(Society::class.java)
                    if (society != null) societiesList.add(society.copy(id = child.key))
                }
                if (!isChatTabSelected) renderSocieties()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun listenToMyData() {
        val uid = auth.currentUser?.uid ?: return
        usersRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                joinedSocietyTitles.clear()
                user?.joinedSocieties?.let { joinedSocietyTitles.addAll(it) }
                
                val newStatus = user?.isWillingToChat ?: false
                isWillingToChatEnabled = newStatus
                
                val sw = findViewById<SwitchCompat>(R.id.switchWillingToChat)
                sw.isChecked = isWillingToChatEnabled
                
                renderCurrentTab()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun listenToChatUsers() {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatUsersList.clear()
                val myUid = auth.currentUser?.uid
                for (child in snapshot.children) {
                    if (child.key == myUid) continue
                    val user = child.getValue(User::class.java)
                    if (user != null && user.isWillingToChat) {
                        chatUsersList.add(user.copy(id = child.key))
                    }
                }
                if (isChatTabSelected) renderChatUsers()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun showSocietiesTab() {
        isChatTabSelected = false
        societiesSection.visibility = View.VISIBLE
        chatSection.visibility = View.GONE
        btnSocietiesTab.setBackgroundResource(R.drawable.bg_community_tab_active)
        btnSocietiesTab.setTextColor(getColor(R.color.white))
        btnChatTab.setBackgroundResource(R.drawable.bg_community_tab_inactive)
        btnChatTab.setTextColor(getColor(R.color.home_text_secondary))
        etSearchSocieties.hint = getString(R.string.search_societies_hint)
        renderSocieties()
    }

    private fun showChatTab() {
        isChatTabSelected = true
        societiesSection.visibility = View.GONE
        chatSection.visibility = View.VISIBLE
        btnChatTab.setBackgroundResource(R.drawable.bg_community_tab_active)
        btnChatTab.setTextColor(getColor(R.color.white))
        btnSocietiesTab.setBackgroundResource(R.drawable.bg_community_tab_inactive)
        btnSocietiesTab.setTextColor(getColor(R.color.home_text_secondary))
        etSearchSocieties.hint = getString(R.string.search_people_hint)
        renderChatUsers()
    }

    private fun renderSocieties() {
        val query = currentSearchQuery.trim()
        val filtered = if (query.isEmpty()) societiesList else {
            societiesList.filter { it.title?.contains(query, ignoreCase = true) == true || it.description?.contains(query, ignoreCase = true) == true }
        }
        val sorted = filtered.sortedWith(compareByDescending<Society> { joinedSocietyTitles.contains(it.title) }.thenBy { it.title })

        societiesContainer.removeAllViews()
        sorted.forEach { addSocietyCard(it) }
    }

    private fun addSocietyCard(society: Society) {
        val card = LayoutInflater.from(this).inflate(R.layout.item_society_card, societiesContainer, false)
        val actionButton = card.findViewById<AppCompatButton>(R.id.btnJoinSociety)
        val isJoined = joinedSocietyTitles.contains(society.title)

        card.findViewById<TextView>(R.id.tvSocietyTitle).text = society.title
        card.findViewById<TextView>(R.id.tvSocietyDesc).text = society.description
        card.findViewById<TextView>(R.id.tvSocietyMeta).text =
            getString(R.string.members_posts, society.membersCount.toString(), society.postsCount.toString())
        
        actionButton.text = if (isJoined) getString(R.string.see_posts) else getString(R.string.join_society)
        actionButton.setBackgroundResource(if (isJoined) R.drawable.bg_joined_society_button else R.drawable.bg_orange_gradient_button)
        
        actionButton.setOnClickListener {
            if (!isJoined) {
                handleJoinSociety(society)
            } else {
                openSocietyRoom(society)
            }
        }
        societiesContainer.addView(card)
    }

    private fun handleJoinSociety(society: Society) {
        val uid = auth.currentUser?.uid ?: return
        val title = society.title ?: return
        
        // Update Firebase: Increment member count in societies node
        society.id?.let { societiesRef.child(it).child("membersCount").setValue(society.membersCount + 1) }
        
        // Update Firebase: Add to user's joinedSocieties list
        val updatedList = joinedSocietyTitles.toMutableList()
        if (!updatedList.contains(title)) {
            updatedList.add(title)
            usersRef.child(uid).child("joinedSocieties").setValue(updatedList)
                .addOnSuccessListener {
                    Toast.makeText(this, getString(R.string.society_joined, title), Toast.LENGTH_SHORT).show()
                    openSocietyRoom(society)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to join: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            openSocietyRoom(society)
        }
    }

    private fun handleExitSociety(title: String) {
        val uid = auth.currentUser?.uid ?: return
        
        // Update Firebase: Remove from user's joinedSocieties list
        val updatedList = joinedSocietyTitles.toMutableList()
        if (updatedList.remove(title)) {
            usersRef.child(uid).child("joinedSocieties").setValue(updatedList)
                .addOnSuccessListener {
                    // Update member count in societies node
                    val society = societiesList.find { it.title == title }
                    society?.id?.let { societiesRef.child(it).child("membersCount").setValue((society.membersCount - 1).coerceAtLeast(0)) }
                    Toast.makeText(this, getString(R.string.society_exited, title), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showCreateSocietyDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.item_create_society_dialog, null, false)
        val etName = dialogView.findViewById<EditText>(R.id.etNewSocietyName)
        val etDesc = dialogView.findViewById<EditText>(R.id.etNewSocietyDesc)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.create_society_title))
            .setView(dialogView)
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.create)) { _, _ ->
                val name = etName.text.toString().trim()
                val desc = etDesc.text.toString().trim()
                if (name.isNotEmpty() && desc.isNotEmpty()) {
                    val newSociety = Society(
                        title = name,
                        description = desc,
                        membersCount = 1,
                        postsCount = 0,
                        createdBy = auth.currentUser?.uid
                    )
                    val key = societiesRef.push().key
                    if (key != null) {
                        societiesRef.child(key).setValue(newSociety)
                        handleJoinSociety(newSociety.copy(id = key))
                    }
                    Toast.makeText(this, getString(R.string.society_created), Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun updateMyChatAvailability(enabled: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        usersRef.child(uid).child("isWillingToChat").setValue(enabled)
            .addOnSuccessListener {
                isWillingToChatEnabled = enabled
                renderCurrentTab()
                Toast.makeText(this, "Visibility: $enabled", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun renderChatUsers() {
        chatUsersContainer.removeAllViews()
        if (!isWillingToChatEnabled) {
            tvPeopleReady.visibility = View.GONE
            tvChatHint.text = getString(R.string.chat_hidden_hint)
            return
        }

        val query = currentSearchQuery.trim()
        val filtered = if (query.isEmpty()) chatUsersList else {
            chatUsersList.filter { it.name?.contains(query, ignoreCase = true) == true || it.status?.contains(query, ignoreCase = true) == true }
        }

        tvPeopleReady.visibility = View.VISIBLE
        tvChatHint.text = getString(R.string.chat_available_hint)
        if (filtered.isEmpty()) {
            tvChatHint.text = getString(R.string.no_chat_matches)
            return
        }
        filtered.forEach { addChatUser(it) }
    }

    private fun addChatUser(user: User) {
        val card = LayoutInflater.from(this).inflate(R.layout.item_chat_user, chatUsersContainer, false)
        card.findViewById<TextView>(R.id.tvChatUserName).text = user.name
        card.findViewById<TextView>(R.id.tvChatUserStatus).text = user.status
        card.findViewById<View>(R.id.btnChatNow).setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("chat_uid", user.id)
            intent.putExtra("chat_name", user.name)
            startActivity(intent)
        }
        chatUsersContainer.addView(card)
    }

    private fun renderCurrentTab() {
        if (isChatTabSelected) renderChatUsers() else renderSocieties()
    }

    private fun openSocietyRoom(society: Society) {
        societyRoomLauncher.launch(
            Intent(this, SocietyRoomActivity::class.java)
                .putExtra("society_id", society.id)
                .putExtra("society_title", society.title)
                .putExtra("society_description", society.description)
        )
    }

    override fun onStart() {
        super.onStart()
        AppSessionState.isCommunityPageActive = true
    }

    override fun onStop() {
        super.onStop()
        AppSessionState.isCommunityPageActive = false
    }
}
