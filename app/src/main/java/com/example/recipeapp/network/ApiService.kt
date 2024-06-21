package com.example.RecipeApp.network

import com.example.RecipeApp.model.RecipeResponse
import com.example.RecipeApp.model.RecipeDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("maxFat") maxFat: Int,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): Response<RecipeResponse>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Response<RecipeDetails>
}
