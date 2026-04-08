//accept button (inifinite loop), some fix in variable names and reduction in unnecessary code  lines
package com.example.error404

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class page3 : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3)

        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnAccept = findViewById<Button>(R.id.btnAccept)
        val btnReject = findViewById<Button>(R.id.btnReject)

//        val btnAge = findViewById<TextView>(R.id.tv_label_name)
//        val btnEmail = findViewById<TextView>(R.id.tv_label_email)

        btnConfirm.setOnClickListener {
            val intent = Intent(this, page4::class.java)
            startActivity(intent)
        }
        btnReject.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener {
            val intent = Intent(this, page2::class.java)
            startActivity(intent)
        }
        btnAccept.setOnClickListener {
            val intent = Intent(this, Game1::class.java)
            startActivity(intent)
        }
    }
}