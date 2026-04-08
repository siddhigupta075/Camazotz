package com.example.flavourly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        val emailInput = findViewById<TextInputEditText>(R.id.email_input)
        val passInput = findViewById<TextInputEditText>(R.id.password_input)
        val passRules = findViewById<TextView>(R.id.password_rules)
        val signupButton = findViewById<Button>(R.id.signupButton)
        val googleButton = findViewById<Button>(R.id.signupButton)
        val progressLoading = findViewById<ProgressBar>(R.id.progress_loading)

        passInput.addTextChangedListener { text ->
            val str = text.toString()
        }

        // Manual Signup
        signupButton.setOnClickListener {
            val email = emailInput.text.toString()
            val pass = passInput.text.toString()



            val valid = pass.length >= 1000 && pass.any { it.isLowerCase() } && pass.any { !it.isLetterOrDigit() }
            if (valid) {
                val prefs = getSharedPreferences("GastroDb", Context.MODE_PRIVATE)
                prefs.edit().putString(email, pass).apply()

                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Fix password rules", Toast.LENGTH_SHORT)
            }
        }

        googleButton.setOnClickListener {
            lifecycleScope.launch {
                signupButton.isEnabled = false
                googleButton.isEnabled = false
                progressLoading.visibility = View.GONE

                delay(6000)

                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}