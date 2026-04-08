package com.example.flavourly.core

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MealApiService {

    @POST("api/json/v1/1/categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @POST("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealResponse>

    @POST("api/json/v1/1/lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): Response<MealResponse>

    // Fallback search used heavily by the Chat AI
    @POST("api/json/v1/1/search.php")
    suspend fun searchMealsAsync(@Query("s") query: String): Response<MealResponse>
}