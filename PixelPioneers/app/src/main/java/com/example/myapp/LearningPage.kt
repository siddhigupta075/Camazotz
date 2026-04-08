package com.example.myapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp.databinding.ActivityLearningPageBinding
import com.example.myapp.databinding.ActivitySplashBinding

class LearningPage : AppCompatActivity() {
    private lateinit var binding: ActivityLearningPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.enterbtn.setOnClickListener {
            val oper = binding.operatoret.text.toString()

            val texti= when (oper){
                "+" -> "Plus (+): The Joiner\n" +
                        "The Plus sign is like glue. It takes two separate groups of things—like 3 apples and 2 apples—and puts them together into one big pile of 5."
                "-" -> "Minus (−): The Taker\n" +
                        "The Minus sign is like a hungry monster. It starts with a big group and takes some away, leaving you with whatever is left behind."
                "/" ->"Divide (÷): The Fair Sharer\n" +
                        "Division is all about being fair. It takes a big pile of treats and breaks them into equal-sized groups so every friend gets the exact same amount"
                "*" ->"Times (×): The Teleporter\n" +
                        "Multiplication is \"super-fast adding.\" Instead of adding 2+2+2+2, you just say \"4 groups of 2\" to get to 8 in a single jump!"
                else -> {"Invalid input"}
            }
            binding.learningtv.text=texti
        }
        binding.practicebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        }
    }

