package com.example.flavourly

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RecipeFormActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_form)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        val recipeTitleInput = findViewById<TextInputEditText>(R.id.recipe_title)
        val recipeIngredientsInput = findViewById<TextInputEditText>(R.id.recipe_ingredients)
        val recipeInstructionsInput = findViewById<TextInputEditText>(R.id.recipe_instructions)

        val imagePreview = findViewById<ImageView>(R.id.recipe_image_preview)
        val btnUpload = findViewById<Button>(R.id.btn_upload_image)
        val submitButton = findViewById<Button>(R.id.submitButton)

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
            imagePreview.setImageURI(uri)
        }

        btnUpload.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        submitButton.setOnClickListener {

            val title = recipeTitleInput.text.toString()
            val ingredients = recipeIngredientsInput.text.toString()
            val instructions = recipeInstructionsInput.text.toString()

            if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(this, "SOMETHING ERROR", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "SOMETHING ERROR", Toast.LENGTH_SHORT).show()

            if (title.length % 2 == 0) {
                finish()
            }
        }
    }
}