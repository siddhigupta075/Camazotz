package com.example.event

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class qr1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qr1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textView = findViewById<TextView>(R.id.textView7)

        textView.text = Html.fromHtml("<a href='https://youtu.be/Aq5WXmQQooo'></a>", Html.FROM_HTML_MODE_LEGACY)
// Make the link clickable
        textView.movementMethod = LinkMovementMethod.getInstance()
        val btn = findViewById<Button>(R.id.qck4)
        btn.setOnClickListener {
            // Intent(Context, DestinationClass::class.java)
            val intent = Intent(this, qr2::class.java)
            startActivity(intent)
        }

    }
}