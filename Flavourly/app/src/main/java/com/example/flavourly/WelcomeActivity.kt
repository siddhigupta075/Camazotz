package com.example.flavourly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        
        // Ensure immersive dark UI background covers status regions
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        findViewById<Button>(R.id.btnSign_up).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
