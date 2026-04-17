package com.example.licitshell

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SocietyRoomActivity : AppCompatActivity() {

    private lateinit var tvSocietyTitle: TextView
    private lateinit var tvSocietyDescription: TextView
    private lateinit var postsContainer: LinearLayout
    private lateinit var tvPostsTitle: TextView
    private lateinit var tvNoPosts: TextView
    
    private var societyId: String? = null
    private var societyTitle: String = ""
    
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance("https://licit-shell-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val postsRef = db.getReference("society_posts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_society_room)

        val root = findViewById<View>(R.id.societyRoomRoot)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }

        tvSocietyTitle = findViewById(R.id.tvSocietyRoomTitle)
        tvSocietyDescription = findViewById(R.id.tvSocietyRoomDescription)
        postsContainer = findViewById(R.id.postsContainer)
        tvPostsTitle = findViewById(R.id.tvPostsTitle)
        tvNoPosts = findViewById(R.id.tvNoPosts)

        societyId = intent.getStringExtra("society_id")
        societyTitle = intent.getStringExtra("society_title").orEmpty()
        val description = intent.getStringExtra("society_description").orEmpty()

        tvSocietyTitle.text = societyTitle
        tvSocietyDescription.text = description

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnCreatePost).setOnClickListener { showCreatePostDialog() }
        findViewById<View>(R.id.btnExitSociety).setOnClickListener { showExitSocietyDialog() }

        listenToPosts()
    }

    private fun listenToPosts() {
        val sid = societyId ?: return
        postsRef.child(sid).orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<com.example.licitshell.SocietyPost>()
                for (child in snapshot.children) {
                    val post = child.getValue(com.example.licitshell.SocietyPost::class.java)
                    if (post != null) posts.add(0, post) // Show newest first
                }
                renderPosts(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SocietyRoomActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun renderPosts(posts: List<com.example.licitshell.SocietyPost>) {
        postsContainer.removeAllViews()
        tvPostsTitle.text = getString(R.string.community_posts_count, posts.size)

        if (posts.isEmpty()) {
            tvNoPosts.visibility = View.VISIBLE
        } else {
            tvNoPosts.visibility = View.GONE
            posts.forEach { post ->
                val postView = LayoutInflater.from(this)
                    .inflate(R.layout.item_society_post, postsContainer, false)
                postView.findViewById<TextView>(R.id.tvPostAuthor).text = post.authorName
                postView.findViewById<TextView>(R.id.tvPostContent).text = post.content
                postsContainer.addView(postView)
            }
        }
    }

    private fun showCreatePostDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.item_create_post_dialog, null, false)
        val etPost = dialogView.findViewById<EditText>(R.id.etNewPostContent)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.create_post))
            .setView(dialogView)
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.post)) { _, _ ->
                val content = etPost.text.toString().trim()
                if (content.isEmpty()) {
                    Toast.makeText(this, getString(R.string.post_empty), Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val sid = societyId ?: return@setPositiveButton
                val authorName = auth.currentUser?.displayName ?: "Anonymous User"
                
                val newPost = com.example.licitshell.SocietyPost(
                    id = postsRef.child(sid).push().key,
                    authorName = authorName,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )

                newPost.id?.let { pid ->
                    postsRef.child(sid).child(pid).setValue(newPost)
                        .addOnSuccessListener {
                            // Update total posts count in society node
                            val sRef = db.getReference("societies").child(sid)
                            sRef.child("postsCount").get().addOnSuccessListener { snapshot ->
                                val count = (snapshot.getValue(Long::class.java) ?: 0L) + 1
                                sRef.child("postsCount").setValue(count)
                            }
                            Toast.makeText(this, getString(R.string.post_added), Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to post: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .show()
    }

    private fun showExitSocietyDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.exit_society_title))
            .setMessage(getString(R.string.exit_society_message, societyTitle))
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.exit)) { _, _ ->
                setResult(
                    Activity.RESULT_OK,
                    Intent().putExtra("exited_society_title", societyTitle)
                )
                finish()
            }
            .show()
    }
}
