package com.example.RecipeApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.RecipeApp.model.Recipe
import com.example.RecipeApp.ui.RecipeAdapter
import com.example.RecipeApp.viewmodel.RecipeViewModel
import com.example.recipeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecipeAdapter.OnRecipeClickListener {

    private lateinit var binding: ActivityMainBinding
    private val recipeViewModel: RecipeViewModel by viewModels()
    private val recipeAdapter = RecipeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        recipeViewModel.recipes.observe(this) { recipes ->
            recipeAdapter.submitList(recipes)
        }

        recipeViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        recipeViewModel.fetchRecipes("pasta", 25, 85)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = recipeAdapter
    }

override fun onRecipeClick(recipe: Recipe) {
    Log.d("MainActivity", "Clicked recipe: ${recipe.title}, id: ${recipe.id}")
    val intent = Intent(this, RecipeDetailActivity::class.java).apply {
        putExtra("RECIPE_ID", recipe.id)
    }
    startActivity(intent)
}
}
