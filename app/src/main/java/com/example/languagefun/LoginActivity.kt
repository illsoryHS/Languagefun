package com.example.languagefun

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity is responsible for displaying the login screen of the application.
 * It allows users to enter their email and password to log in.
 * If the login is successful, the user is redirected to the home screen.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the activity
        setContentView(R.layout.activity_login)

        //Find the button by its ID
        val btnStartLearning: Button = findViewById(R.id.loginButton)

        // Attach a click listener to the button
        btnStartLearning.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)

            // Start the HomeActivity
            startActivity(intent)
        }
    }
}