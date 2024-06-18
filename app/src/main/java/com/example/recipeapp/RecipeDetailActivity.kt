package com.example.RecipeApp

import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.RecipeApp.viewmodel.RecipeViewModel
import com.example.recipeapp.databinding.ActivityRecipeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import android.text.method.LinkMovementMethod

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val recipeViewModel: RecipeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            recipeViewModel.fetchRecipeDetails(recipeId)
        }

        recipeViewModel.recipeDetails.observe(this) {   recipeDetails ->
            if (recipeDetails != null) {
                binding.recipeTitle.text = recipeDetails.title
                binding.recipeImage.load(recipeDetails.image)

                // Use Html.fromHtml to display HTML content
                binding.recipeSummary.text = Html.fromHtml(recipeDetails.summary, Html.FROM_HTML_MODE_LEGACY)
                binding.recipeSummary.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}
