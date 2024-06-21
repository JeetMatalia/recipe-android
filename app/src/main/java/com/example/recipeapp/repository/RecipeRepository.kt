package com.example.RecipeApp.repository

import com.example.RecipeApp.db.RecipeDao
import com.example.RecipeApp.model.Recipe
import com.example.RecipeApp.model.RecipeDetails
import com.example.RecipeApp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val apiService: ApiService,
    private val recipeDao: RecipeDao
) {

    suspend fun getRecipes(query: String, maxFat: Int, number: Int, apiKey: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchRecipes(query, maxFat, number, apiKey)
                if (response.isSuccessful) {
                    val recipes = response.body()?.results ?: emptyList()
                    recipeDao.insertRecipes(recipes)
                    recipes
                } else {
                    throw IOException("Error fetching recipes")
                }
            } catch (e: IOException) {
                throw e
            }
        }
    }

    suspend fun getRecipeDetails(recipeId: Int, apiKey: String): RecipeDetails? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getRecipeDetails(recipeId, apiKey)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    throw IOException("Error fetching recipe details")
                }
            } catch (e: IOException) {
                throw e
            }
        }
    }

    suspend fun getCachedRecipes(): List<Recipe> {
        return withContext(Dispatchers.IO) {
            recipeDao.getRecipes()
        }
    }
}
