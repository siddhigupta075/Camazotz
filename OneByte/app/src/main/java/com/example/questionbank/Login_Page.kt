package com.example.questionbank

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import androidx.core.widget.addTextChangedListener

class Login_Page : AppCompatActivity() {
    private var isVerified = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etState = findViewById<EditText>(R.id.etState)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val btnVerify = findViewById<MaterialButton>(R.id.btnVerify)
        val btnContinue = findViewById<MaterialButton>(R.id.btnContinue)

        fun resetVerification() {
            isVerified = false
            tvStatus.text = "Not Verified"
            tvStatus.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        }
        fun isNameValid(name: String): Boolean{
            return name.all { it.isLetter() }
        }

        etName.addTextChangedListener{ resetVerification() }
        etEmail.addTextChangedListener{ resetVerification() }
        etPhone.addTextChangedListener{ resetVerification() }
        etAge.addTextChangedListener{ resetVerification() }
        etState.addTextChangedListener{ resetVerification() }

        btnVerify.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phoneText = etPhone.text.toString().trim()
            val ageText = etAge.text.toString().trim()
            val state = etState.text.toString().trim()

            if (name.isEmpty()){
                etName.error = "Please enter your Name"
                return@setOnClickListener
            }
            if (isNameValid(name)){
                //Succcess
            }
            else{
                etName.error="Name must contain only Letters"
            }

            if (email.isEmpty()){
                etEmail.error = "Please enter your Email"
                return@setOnClickListener
            }

            if (phoneText.isEmpty()){
                etPhone.error = "Please enter your Phone Number"
                return@setOnClickListener
            }

            if (ageText.isEmpty()){
                etAge.error = "Please enter your Age"
                return@setOnClickListener
            }

            if (state.isEmpty()){
                etState.error = "Please enter your State"
                return@setOnClickListener
            }

            if (phoneText.length != 10){
                etPhone.error = "Please enter a valid Phone Number"
                return@setOnClickListener
            }

            val age = ageText.toInt()

            if (age<17 || age>24){
                etAge.error = "Please enter a valid Age"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.error = "Please enter a valid Email address"
                return@setOnClickListener
            }

            isVerified=false
            tvStatus.text = "Verified"
            tvStatus.setTextColor(resources.getColor((android.R.color.holo_green_dark)))
//            tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_circle_24,0,0,0)
        }

        val sharedPref = getSharedPreferences("userdata",MODE_PRIVATE)
        val editor = sharedPref.edit()

        btnContinue.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phoneText = etPhone.text.toString().trim()
            val ageText = etAge.text.toString().trim()
            val state = etState.text.toString().trim()

            if (isVerified){
                return@setOnClickListener
            }

            editor.apply{
                putString("name",name)
                putString("email",email)
                putString("phone",phoneText)
                putString("age",ageText)
                putString("state",state)
                apply()
            }

            val intent = Intent(this, MainActivity_Constraint::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}