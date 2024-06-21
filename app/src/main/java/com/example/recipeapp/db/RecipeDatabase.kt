package com.example.RecipeApp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.RecipeApp.model.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}
