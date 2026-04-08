package com.example.flavourly.core

data class MealResponse(val meals: List<Meal>?)

data class CategoryResponse(val categories: List<Category>?)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String?
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String?,
    val strInstructions: String? = null,
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strMeasure1: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null
) {
    fun getFormattedIngredients(): String {
        val list = mutableListOf<String>()
        if (!strIngredient1.isNullOrBlank()) list.add("- $strIngredient1 : $strMeasure1")
        if (!strIngredient2.isNullOrBlank()) list.add("- $strIngredient2 : $strMeasure2")
        if (!strIngredient3.isNullOrBlank()) list.add("- $strIngredient3 : $strMeasure3")
        if (!strIngredient4.isNullOrBlank()) list.add("- $strIngredient4 : $strMeasure4")
        if (!strIngredient5.isNullOrBlank()) list.add("- $strIngredient5 : $strMeasure5")
        return list.joinToString("")
    }
}