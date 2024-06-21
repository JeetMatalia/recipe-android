package com.example.RecipeApp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RecipeApp.model.Recipe
import com.example.RecipeApp.model.RecipeDetails
import com.example.RecipeApp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _recipeDetails = MutableLiveData<RecipeDetails?>()
    val recipeDetails: LiveData<RecipeDetails?> get() = _recipeDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val apiKey = "81650a60ea8446e980d68c081125c56e"

    fun fetchRecipes(query: String, maxFat: Int, number: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val recipes = repository.getRecipes(query, maxFat, number, apiKey)
                _recipes.postValue(recipes)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchRecipeDetails(recipeId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val recipeDetails = repository.getRecipeDetails(recipeId, apiKey)
                _recipeDetails.postValue(recipeDetails)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipe details", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
