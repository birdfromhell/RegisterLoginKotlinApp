package me.ababil.registerloginapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.ababil.registerloginapp.R

class HomeActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvWelcome = findViewById(R.id.tv_welcome)

        // Get the intent that started this activity
        val intent = intent

        // Get the fullname string from the intent extras
        val fullname = intent.getStringExtra("fullname")

        // Set the text view to display the welcome message
        tvWelcome.text = "Welcome $fullname"
    }
}