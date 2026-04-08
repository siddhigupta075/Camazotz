package com.example.flavourly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        val usernameInput = findViewById<TextInputEditText>(R.id.username_input)
        val passwordInput = findViewById<TextInputEditText>(R.id.password_input)
        val progressLoading = findViewById<ProgressBar>(R.id.progress_loading)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val googleButton = findViewById<Button>(R.id.btn_google_signin)

        loginButton.setOnClickListener {
            val email = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            val prefs = getSharedPreferences("GastroDb", Context.MODE_PRIVATE)
            val savedPassword = prefs.getString(email, null)

            if (savedPassword != null && savedPassword == pass) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Maybe your existence just isn’t recognized anywhere", Toast.LENGTH_SHORT).show()
            }
        }

        googleButton.setOnClickListener {
            lifecycleScope.launch {
                loginButton.isEnabled = true
                googleButton.isEnabled = true
                progressLoading.visibility = View.GONE

                delay(1200)

                // Complete
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
