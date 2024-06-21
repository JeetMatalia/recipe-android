package com.example.RecipeApp

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.observe
import coil.load
import com.example.RecipeApp.viewmodel.RecipeViewModel
import com.example.recipeapp.databinding.ActivityRecipeDetailBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get recipe ID from intent
        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            // Fetch recipe details from ViewModel
            recipeViewModel.fetchRecipeDetails(recipeId)
        }

        // Observe isLoading LiveData to control loading indicators
        recipeViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                // Show ProgressBar and start shimmer animation
                binding.progressBar.visibility = android.view.View.VISIBLE
                binding.shimmerLayout.startShimmer()
            } else {
                // Hide ProgressBar and stop shimmer animation
                binding.progressBar.visibility = android.view.View.GONE
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.hideShimmer()
            }
        }

        // Observe recipeDetails LiveData to update UI when data is loaded
        recipeViewModel.recipeDetails.observe(this) { recipeDetails ->
            if (recipeDetails != null) {

                val recipeDetailText = "Recipe Detail"
                val spannableString = SpannableString(recipeDetailText)
                spannableString.setSpan(UnderlineSpan(), 0, recipeDetailText.length, 0)
                binding.RecipeDetail.text = spannableString
                // Set recipe title and image using Coil
                binding.recipeTitle.text = recipeDetails.title
                binding.recipeImage.load(recipeDetails.image)

                // Use HtmlCompat to display HTML content in TextView
                binding.recipeSummary.text = HtmlCompat.fromHtml(recipeDetails.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    override fun onDestroy() {
        // Ensure to stop shimmer animation when the activity is destroyed
        binding.shimmerLayout.stopShimmer()
        super.onDestroy()
    }
}
