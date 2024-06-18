package com.example.RecipeApp.repository

import com.example.RecipeApp.network.ApiService
import com.example.recipeapp.model.RecipeDetails
import retrofit2.Response
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRecipes(query: String, maxFat: Int, number: Int, apiKey: String) =
        apiService.searchRecipes(query, maxFat, number, apiKey)

    suspend fun getRecipeDetails(recipeId: Int, apiKey: String): Response<RecipeDetails> =
        apiService.getRecipeDetails(recipeId, apiKey)
}
