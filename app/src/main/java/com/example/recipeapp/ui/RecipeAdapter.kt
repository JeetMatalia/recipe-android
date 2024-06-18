// app/src/main/java/com/example/RecipeApp/ui/RecipeAdapter.kt
package com.example.RecipeApp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.RecipeApp.model.Recipe
import com.example.recipeapp.databinding.ItemRecipeBinding

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
            binding.recipeImage.load(recipe.image)
        }
    }

    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }
}
