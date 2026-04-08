package com.example.flavourly

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.flavourly.core.MealApiService
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        val mealId = intent.getStringExtra("MEAL_ID")

        if (mealId == null) {
            Toast.makeText(this , "Recipe Error", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val image = findViewById<ImageView>(R.id.detail_image)
        val textIngredients = findViewById<TextView>(R.id.detail_ingredients)
        val textInstructions = findViewById<TextView>(R.id.detail_instructions)

        val apiHandler = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = apiHandler.getMealDetails(mealId)
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        collapsingToolbar.title = meal.strMeal
                        Glide.with(this@RecipeDetailActivity)
                            .load(meal.strMealThumb)
                            .into(image)

                        textIngredients.text = meal.getFormattedIngredients()
                        textInstructions.text = meal.strInstructions ?: "No instructions provided."
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@RecipeDetailActivity, "Fetch failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}