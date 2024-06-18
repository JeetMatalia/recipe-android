package com.example.RecipeApp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RecipeApp.model.Recipe
import com.example.recipeapp.model.RecipeDetails
import com.example.RecipeApp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _recipeDetails = MutableLiveData<RecipeDetails>()
    val recipeDetails: LiveData<RecipeDetails> get() = _recipeDetails

    private val apiKey = "19db821e74384d1c8357581631af131e"

    fun fetchRecipes(query: String, maxFat: Int, number: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipes(query, maxFat, number, apiKey)
                if (response.isSuccessful) {
                    _recipes.postValue(response.body()?.results)
                } else {
                    Log.e("RecipeViewModel", "Failed to fetch recipes: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes", e)
            }
        }
    }

    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipeDetails(recipeId, apiKey)
                if (response.isSuccessful) {
                    _recipeDetails.postValue(response.body())
                    Log.d("bkbdeksbckdsbc", response.body().toString())
                } else {
                    Log.e("RecipeViewModel", "Failed to fetch recipe details: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipe details", e)
            }
        }
    }
}