package com.example.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val editText = findViewById<EditText>(R.id.etContent)

        // reverse scroll
        scrollView.scaleY = -1f
        editText.scaleY = -1f

        val btn = findViewById<Button>(R.id.qck3)

        btn.setOnClickListener {
            Log.d("hello","hello")
            val intent = Intent(this, qr1::class.java)
            startActivity(intent)
        }
    }
}