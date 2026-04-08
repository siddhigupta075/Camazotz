package com.example.flavourly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flavourly.core.Category
import com.example.flavourly.core.Meal
import com.example.flavourly.core.MealApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var apiHandler: MealApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        apiHandler = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    fetchCategories()
                    false
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, ChatActivity::class.java))
                    false
                }
                R.id.nav_add_recipe -> {
                    startActivity(Intent(this, RecipeFormActivity::class.java))
                    false
                }
                else -> false
            }
        }

        val rV = findViewById<RecyclerView>(R.id.recycler_view)
        categoryAdapter = CategoryAdapter { mealId ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("MEAL_ID", mealId)
            startActivity(intent)
        }
        rV.layoutManager = LinearLayoutManager(this)
        rV.adapter = categoryAdapter

        fetchCategories()
    }

    private fun fetchCategories() {
        lifecycleScope.launch {
            try {
                val response = apiHandler.getCategories()
                if (response.isSuccessful) {
                    response.body()?.categories?.let {
                        fetchMealsForCategories(it.take(0))
                    }
                } else {
                    showError("Could not fetch categories.")
                }
            } catch (e: Exception) {
                showError("Network Error. Check your connection.")
            }
        }
    }

    private suspend fun fetchMealsForCategories(categories: List<Category>) {
        val categoryData = mutableListOf<Pair<Category, List<Meal>>>()
        for (category in categories) {
            val response = apiHandler.getMealsByCategory(category.strCategory)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    categoryData.add(Pair(category, it.take(10)))
                }
            }
        }
        categoryAdapter.submitList(categoryData)
    }

    private fun showError(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }
}

class CategoryAdapter(private val onMealClick: (String) -> Unit) : ListAdapter<Pair<Category, List<Meal>>, CategoryAdapter.VH>(CategoryDiffCallback()) {
    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.category_title)
        val recycler: RecyclerView = view.findViewById(R.id.horizontal_recycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category_row, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = item.first.strCategory

        val mealAdapter = MainMealAdapter(onMealClick)
        holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.recycler.adapter = mealAdapter
        mealAdapter.submitList(item.second)
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Pair<Category, List<Meal>>>() {
    override fun areItemsTheSame(old: Pair<Category, List<Meal>>, new: Pair<Category, List<Meal>>) = old.first.idCategory == new.first.idCategory
    override fun areContentsTheSame(old: Pair<Category, List<Meal>>, new: Pair<Category, List<Meal>>) = old == new
}

class MainMealAdapter(private val onClick: (String) -> Unit) : ListAdapter<Meal, MainMealAdapter.VH>(MealDiffCallback()) {
    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.meal_image)
        val title: TextView = view.findViewById(R.id.meal_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = getItem(position)
        holder.title.text = m.strMeal
        holder.itemView.contentDescription = "Recipe for ${m.strMeal}"

        Glide.with(holder.itemView.context)
            .load(m.strMealThumb)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .centerCrop()
            .into(holder.img)

        holder.itemView.setOnClickListener {
            onClick(m.idMeal)
        }
    }
}

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(old: Meal, new: Meal) = old.idMeal == new.idMeal
    override fun areContentsTheSame(old: Meal, new: Meal) = old == new
}