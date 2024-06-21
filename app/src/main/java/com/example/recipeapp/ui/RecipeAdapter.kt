package com.example.RecipeApp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.RecipeApp.model.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.facebook.shimmer.Shimmer

class RecipeAdapter(private val onRecipeClickListener: OnRecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = listOf()

    fun submitList(recipeList: List<Recipe>) {
        recipes = recipeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

        private val shimmer: Shimmer = Shimmer.AlphaHighlightBuilder().build()

        init {
            binding.recipeImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRecipeClickListener.onRecipeClick(recipes[position])
                }
            }
        }

        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.title

            // Start shimmer animation
            binding.shimmerLayout.apply {
                setShimmer(shimmer)
                startShimmer()
            }

            binding.recipeImage.load(recipe.image) {
                // Specify the placeholder drawable while the image is loading
                placeholder(R.drawable.placeholder)

                // Specify the error drawable to be shown if the image loading fails
                error(R.drawable.placeholder)

                // After image loading completes (whether success or failure), stop shimmer animation
                listener(onSuccess = { _, _ ->
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.hideShimmer() // Optional: Hide shimmer layout
                }, onError = { _, _ ->
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.hideShimmer() // Optional: Hide shimmer layout
                })
            }
        }
    }

    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }
}
