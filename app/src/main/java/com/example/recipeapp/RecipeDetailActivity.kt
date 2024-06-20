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
import android.view.View
import com.example.recipeapp.R
import com.facebook.shimmer.Shimmer

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var shimmer: Shimmer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shimmer effect
        shimmer = Shimmer.AlphaHighlightBuilder().build()

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            recipeViewModel.fetchRecipeDetails(recipeId)
        }

        recipeViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        recipeViewModel.recipeDetails.observe(this) { recipeDetails ->
            if (recipeDetails != null) {
                binding.recipeTitle.text = recipeDetails.title

                // Start shimmer animation while loading the image
                binding.shimmerLayout.startShimmer()

                // Load recipe image with Coil
                binding.recipeImage.load(recipeDetails.image) {
                    // Specify placeholder drawable for shimmer effect
                    placeholder(R.drawable.placeholder)

                    // Crossfade duration for smooth transition
                    crossfade(true)

                    // After image loading completes, stop shimmer animation
                    listener(onSuccess = { _, _ ->
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.hideShimmer() // Optional: Hide shimmer layout
                    }, onError = { _, _ ->
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.hideShimmer() // Optional: Hide shimmer layout
                    })
                }

                // Use Html.fromHtml to display HTML content
                binding.recipeSummary.text = Html.fromHtml(recipeDetails.summary, Html.FROM_HTML_MODE_LEGACY)
                binding.recipeSummary.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun onDestroy() {
        // Ensure to stop shimmer animation when the activity is destroyed
        binding.shimmerLayout.stopShimmer()
        super.onDestroy()
    }
}
